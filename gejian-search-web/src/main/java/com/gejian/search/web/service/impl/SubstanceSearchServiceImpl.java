package com.gejian.search.web.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gejian.common.core.constant.SecurityConstants;
import com.gejian.common.core.util.R;
import com.gejian.search.common.constant.SubstanceOnlineIndexConstant;
import com.gejian.search.common.dto.SubstanceSearchDTO;
import com.gejian.search.common.enums.SearchTypeEnum;
import com.gejian.search.common.index.SubstanceOnlineIndex;
import com.gejian.search.web.service.SubstanceSearchService;
import com.gejian.substance.client.dto.online.app.view.OnlineSearchDTO;
import com.gejian.substance.client.dto.online.rpc.RpcOnlineSearchDTO;
import com.gejian.substance.client.feign.RemoteSubstanceService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_CLASSIFY_ID;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_CREATE_USER_NICKNAME;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_DELETED;
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
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private RemoteSubstanceService remoteSubstanceService;

    @Override
    public Page<OnlineSearchDTO> search(SubstanceSearchDTO substanceSearchDTO) {

        if(!StringUtils.hasText(substanceSearchDTO.getContent())){
            return new Page<>();
        }
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        if(substanceSearchDTO.getSearchType() == null || substanceSearchDTO.getSearchType() == SearchTypeEnum.VIDEO){
            boolQueryBuilder.must(QueryBuilders.multiMatchQuery(substanceSearchDTO.getContent(), FIELD_VIDEO_TITLE, SubstanceOnlineIndexConstant.FIELD_VIDEO_INTRODUCE));
        }else{
            boolQueryBuilder.must(QueryBuilders.matchQuery(FIELD_CREATE_USER_NICKNAME,substanceSearchDTO.getContent()));
        }
        if(substanceSearchDTO.getVideoLengthGt() != null || substanceSearchDTO.getVideoLengthLt() != null){
            if(substanceSearchDTO.getVideoLengthGt() != null){
                boolQueryBuilder.must(QueryBuilders.rangeQuery(FIELD_VIDEO_LENGTH).gte(substanceSearchDTO.getVideoLengthGt() * 60 * 1000));
            }
            if(substanceSearchDTO.getVideoLengthLt() != null){
                boolQueryBuilder.must(QueryBuilders.rangeQuery(FIELD_VIDEO_LENGTH).lte(substanceSearchDTO.getVideoLengthLt() * 60 * 1000));
            }
        }
        if(substanceSearchDTO.getClassifyId() != null){
            boolQueryBuilder.must(QueryBuilders.termQuery(FIELD_CLASSIFY_ID,substanceSearchDTO.getClassifyId()));
        }
        boolQueryBuilder.must(QueryBuilders.termQuery(FIELD_DELETED,false));
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(boolQueryBuilder);
        PageRequest pageRequest = page(substanceSearchDTO);
        nativeSearchQuery.setPageable(pageRequest);
        SearchHits<SubstanceOnlineIndex> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, SubstanceOnlineIndex.class);
        if(searchHits.getTotalHits() <= 0){
            return new Page<>();
        }
        List<SubstanceOnlineIndex> contents = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        RpcOnlineSearchDTO rpcOnlineSearchDTO = new RpcOnlineSearchDTO();
        rpcOnlineSearchDTO.setIds(contents.stream().map(SubstanceOnlineIndex::getId).collect(Collectors.toList()));
        final R<List<OnlineSearchDTO>> listR = remoteSubstanceService.searchOnlineAndCountByIds(rpcOnlineSearchDTO, SecurityConstants.FROM_IN);
        return new Page<OnlineSearchDTO>(substanceSearchDTO.getCurrent(),substanceSearchDTO.getSize(),searchHits.getTotalHits()).setRecords(listR.getData());
    }

    private PageRequest page(SubstanceSearchDTO substanceSearchDTO){
        if(substanceSearchDTO.getCurrent() == null){
            substanceSearchDTO.setCurrent(1);
        }
        if(substanceSearchDTO.getSize() == null){
            substanceSearchDTO.setSize(10);
        }

        PageRequest pageRequest = PageRequest.of((substanceSearchDTO.getCurrent() - 1) * substanceSearchDTO.getSize(), substanceSearchDTO.getSize());
        if(substanceSearchDTO.getOrderField() != null){
            Optional<String> fieldMapping = substanceSearchDTO.getOrderField().fieldMapping();
            if(fieldMapping.isPresent()){
                pageRequest = pageRequest.withSort(Sort.Direction.DESC,fieldMapping.get());
            }
        }
        return pageRequest;
    }




    @Scheduled(cron = "0 0/2 * * * *")
    public void keepESAlive() {
        try {
            restHighLevelClient.info(RequestOptions.DEFAULT);
            log.info("keep es alive");
        } catch (IOException e) {
            log.error("keep es alive error!" ,e);
        }
    }
}
