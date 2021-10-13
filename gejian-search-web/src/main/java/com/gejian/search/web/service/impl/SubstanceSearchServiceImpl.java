package com.gejian.search.web.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gejian.common.core.constant.SecurityConstants;
import com.gejian.common.core.util.acautomation.ACAutomationSearch;
import com.gejian.common.minio.annotation.MinioResponse;
import com.gejian.common.security.service.GeJianUser;
import com.gejian.search.common.constant.BasicConstant;
import com.gejian.search.common.constant.UserVideoIndexConstant;
import com.gejian.search.common.dto.SubstanceOnlineResponseDTO;
import com.gejian.search.common.dto.SubstanceSearchDTO;
import com.gejian.search.common.dto.UserSearchDTO;
import com.gejian.search.common.enums.SearchTypeEnum;
import com.gejian.search.common.index.SubstanceOnlineIndex;
import com.gejian.search.common.index.UserVideoIndex;
import com.gejian.search.web.executor.AsyncExecutor;
import com.gejian.search.web.service.RedisSearchService;
import com.gejian.search.web.service.SearchHistoryService;
import com.gejian.search.web.service.SubstanceSearchService;
import com.gejian.substance.client.dto.video.UserSearchVideoViewDTO;
import com.gejian.substance.client.dto.video.app.AppUserSearchVideoDTO;
import com.gejian.substance.client.feign.RemoteSubstanceService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_CLASSIFY_ID;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_CREATE_USER_NICKNAME;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_DELETED;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_VIDEO_INTRODUCE;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_VIDEO_LENGTH;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_VIDEO_TITLE;

/**
 * @author ：lijianghuai
 * @date ：2021-08-25 10:17
 * @description：
 */

@Service
@Slf4j
public class SubstanceSearchServiceImpl implements SubstanceSearchService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private RedisSearchService redisSearchService;

    @Autowired
    private RemoteSubstanceService remoteSubstanceService;

    @Autowired
    private SearchHistoryService historySearchBackendService;

    @MinioResponse
    @Override
    public Page<SubstanceOnlineResponseDTO> search(SubstanceSearchDTO substanceSearchDTO) {

        if (!StringUtils.hasText(substanceSearchDTO.getContent())) {
            return new Page<>();
        }
        ACAutomationSearch.SearchResult result = ACAutomationSearch.getInstance().search(substanceSearchDTO.getContent());
        if (result.anyContains()) {
            return new Page<>();
        }
        AsyncExecutor.execute(() -> redisSearchService.setHistorySearch(substanceSearchDTO.getContent()));
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        BoolQueryBuilder innerBoolQueryBuilder = new BoolQueryBuilder();
        if (substanceSearchDTO.getSearchType() == null || substanceSearchDTO.getSearchType() == SearchTypeEnum.VIDEO) {
            innerBoolQueryBuilder.must(QueryBuilders.multiMatchQuery(substanceSearchDTO.getContent(), FIELD_VIDEO_TITLE, FIELD_VIDEO_INTRODUCE).analyzer(BasicConstant.IK_SMART));
        } else {
            innerBoolQueryBuilder.must(QueryBuilders.matchQuery(FIELD_CREATE_USER_NICKNAME, substanceSearchDTO.getContent()));
        }
        if (substanceSearchDTO.getVideoLengthGt() != null || substanceSearchDTO.getVideoLengthLt() != null) {
            if (substanceSearchDTO.getVideoLengthGt() != null) {
                innerBoolQueryBuilder.filter(QueryBuilders.rangeQuery(FIELD_VIDEO_LENGTH).gte(substanceSearchDTO.getVideoLengthGt() * 60 * 1000));
            }
            if (substanceSearchDTO.getVideoLengthLt() != null) {
                innerBoolQueryBuilder.filter(QueryBuilders.rangeQuery(FIELD_VIDEO_LENGTH).lte(substanceSearchDTO.getVideoLengthLt() * 60 * 1000));
            }
        }
        if (substanceSearchDTO.getClassifyId() != null) {
            innerBoolQueryBuilder.filter(QueryBuilders.termQuery(FIELD_CLASSIFY_ID, substanceSearchDTO.getClassifyId()));
        }
        innerBoolQueryBuilder.filter(QueryBuilders.termQuery(FIELD_DELETED, false));
        MatchPhraseQueryBuilder phraseQueryBuilder = new MatchPhraseQueryBuilder(FIELD_VIDEO_TITLE,substanceSearchDTO.getContent());
        boolQueryBuilder.should(innerBoolQueryBuilder).should(phraseQueryBuilder);
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(boolQueryBuilder);
        PageRequest pageRequest = page(substanceSearchDTO);
        nativeSearchQuery.setPageable(pageRequest);
        SearchHits<SubstanceOnlineIndex> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, SubstanceOnlineIndex.class);
        if (searchHits.getTotalHits() <= 0 ) {
            return new Page<>();
        }
        List<SubstanceOnlineResponseDTO> responseList = searchHits.stream().map(searchHit -> {
            SubstanceOnlineResponseDTO substanceOnlineResponseDTO = new SubstanceOnlineResponseDTO();
            BeanUtils.copyProperties(searchHit.getContent(), substanceOnlineResponseDTO);
            return substanceOnlineResponseDTO;
        }).collect(Collectors.toList());
        return new Page<SubstanceOnlineResponseDTO>(substanceSearchDTO.getCurrent(), substanceSearchDTO.getSize(), searchHits.getTotalHits()).setRecords(responseList);
    }

    @Override
    public List<UserSearchVideoViewDTO> searchUserVideo(UserSearchDTO userSearchDTO, GeJianUser geJianUser) {
        if (ObjectUtils.isEmpty(userSearchDTO.getKeywork())) {
            return new ArrayList<>();
        }

        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                // 模糊查询
                .withQuery(QueryBuilders.fuzzyQuery(UserVideoIndexConstant.FIELD_VIDEO_TITLE, userSearchDTO.getKeywork()))
                .withQuery(QueryBuilders.fuzzyQuery(UserVideoIndexConstant.FIELD_VIDEO_INTRODUCE, userSearchDTO.getKeywork()))
                .withQuery(QueryBuilders.termQuery(UserVideoIndexConstant.CREATE_USER_ID, ObjectUtils.isEmpty(userSearchDTO.getLookUserId()) ? geJianUser.getId()
                        : userSearchDTO.getLookUserId()))
                // 分页
                .withPageable(PageRequest.of((userSearchDTO.getCurrent() - 1), userSearchDTO.getSize()))
                .build();
        SearchHits<UserVideoIndex> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, UserVideoIndex.class);
        if (searchHits.getTotalHits() <= 0) {
            return new ArrayList<>();
        }

        List<Long> videoIds = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList()).stream().map(UserVideoIndex::getId).collect(Collectors.toList());

        AppUserSearchVideoDTO appUserSearchVideoDTO = new AppUserSearchVideoDTO();
        appUserSearchVideoDTO.setVideoIds(videoIds);

        return remoteSubstanceService.searchUserVideo(appUserSearchVideoDTO, SecurityConstants.FROM_IN).getData();
    }

    private PageRequest page(SubstanceSearchDTO substanceSearchDTO) {


        if (substanceSearchDTO.getCurrent() == null) {
            substanceSearchDTO.setCurrent(1);
        }
        if (substanceSearchDTO.getSize() == null) {
            substanceSearchDTO.setSize(10);
        }

        PageRequest pageRequest = PageRequest.of((substanceSearchDTO.getCurrent() - 1) , substanceSearchDTO.getSize());
        if (substanceSearchDTO.getOrderField() != null) {
            Optional<String> fieldMapping = substanceSearchDTO.getOrderField().fieldMapping();
            if (fieldMapping.isPresent()) {
                pageRequest = pageRequest.withSort(Sort.Direction.DESC, fieldMapping.get());
            }
        }
        return pageRequest;
    }


}
