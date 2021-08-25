package com.gejian.search.web.service.impl;

import com.gejian.search.common.constant.SubstanceOnlineIndexConstant;
import com.gejian.search.common.dto.SubstanceSearchDTO;
import com.gejian.search.common.enums.SearchTypeEnum;
import com.gejian.search.common.index.SubstanceOnlineIndex;
import com.gejian.search.web.service.SubstanceSearchService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Override
    public Page<SubstanceOnlineIndex> search(SubstanceSearchDTO substanceSearchDTO) {
        if(!StringUtils.hasText(substanceSearchDTO.getContent())){
            return new PageImpl<>(Lists.newArrayList());
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
            return new PageImpl<>(Lists.newArrayList(),pageRequest,0);
        }
        List<SubstanceOnlineIndex> contents = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        return new PageImpl<>(contents,pageRequest,searchHits.getTotalHits());
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
}
