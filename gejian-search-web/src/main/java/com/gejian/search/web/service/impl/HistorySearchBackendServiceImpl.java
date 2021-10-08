package com.gejian.search.web.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gejian.search.common.constant.SearchHistoryIndexConstant;
import com.gejian.search.common.dto.HistorySearchBackendQueryDTO;
import com.gejian.search.common.dto.HistorySearchBackendResultDTO;
import com.gejian.search.common.dto.PopularSearchBackendQueryDTO;
import com.gejian.search.common.dto.PopularSearchBackendResultDTO;
import com.gejian.search.common.index.SearchHistoryIndex;
import com.gejian.search.web.service.HistorySearchBackendService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：lijianghuai
 * @date ：2021-09-07 11:51
 * @description：
 */
@Component
public class HistorySearchBackendServiceImpl implements HistorySearchBackendService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public void insert(String content) {
        SearchHistoryIndex historyIndex = SearchHistoryIndex.builder().content(content).createTime(LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8))).build();
        elasticsearchRestTemplate.save(historyIndex);
    }

    @Override
    public Page<PopularSearchBackendResultDTO> queryPopular(PopularSearchBackendQueryDTO popularBackendSearchDTO) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        if(popularBackendSearchDTO.getStartedAt() != null){
            boolQueryBuilder.must(QueryBuilders.rangeQuery(SearchHistoryIndexConstant.FIELD_CREATE_TIME).gte(popularBackendSearchDTO.getStartedAt().toEpochSecond(ZoneOffset.ofHours(8))));
        }
        if(popularBackendSearchDTO.getTerminatedAt() != null){
            boolQueryBuilder.must(QueryBuilders.rangeQuery(SearchHistoryIndexConstant.FIELD_CREATE_TIME).lte(popularBackendSearchDTO.getTerminatedAt().toEpochSecond(ZoneOffset.ofHours(8))));
        }
        if(StringUtils.hasText(popularBackendSearchDTO.getContent())){
            boolQueryBuilder.must(QueryBuilders.termQuery(SearchHistoryIndexConstant.FIELD_CONTENT,popularBackendSearchDTO.getContent()));
        }
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(boolQueryBuilder);
        nativeSearchQuery.setMaxResults(0);
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("popular").field(SearchHistoryIndexConstant.FIELD_CONTENT).size(100000);
        nativeSearchQuery.addAggregation(termsAggregationBuilder);
        Aggregations aggregations = elasticsearchRestTemplate.search(nativeSearchQuery, SearchHistoryIndex.class).getAggregations();
        if(aggregations != null){
            ParsedStringTerms popular = aggregations.get("popular");
            List<? extends Terms.Bucket> buckets = popular.getBuckets();
            List<PopularSearchBackendResultDTO> popularBackendResultDTOList = sub(buckets, popularBackendSearchDTO).stream().map(bucket -> PopularSearchBackendResultDTO.builder().keyword(bucket.getKeyAsString()).count(bucket.getDocCount()).build()).collect(Collectors.toList());
            int total = buckets.size();
            buckets = null;
            return new Page<PopularSearchBackendResultDTO>(popularBackendSearchDTO.getCurrent(),popularBackendSearchDTO.getSize(),total).setRecords(popularBackendResultDTOList);
        }
        return new Page<>();
    }

    @Override
    public Page<HistorySearchBackendResultDTO> queryHistorySearch(HistorySearchBackendQueryDTO historySearchBackendQueryDTO) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        if(historySearchBackendQueryDTO.getStartedAt() != null){
            boolQueryBuilder.must(QueryBuilders.rangeQuery(SearchHistoryIndexConstant.FIELD_CREATE_TIME).gte(historySearchBackendQueryDTO.getStartedAt().toEpochSecond(ZoneOffset.ofHours(8))));
        }
        if(historySearchBackendQueryDTO.getTerminatedAt() != null){
            boolQueryBuilder.must(QueryBuilders.rangeQuery(SearchHistoryIndexConstant.FIELD_CREATE_TIME).lte(historySearchBackendQueryDTO.getTerminatedAt().toEpochSecond(ZoneOffset.ofHours(8))));
        }
        if(StringUtils.hasText(historySearchBackendQueryDTO.getContent())){
            boolQueryBuilder.must(QueryBuilders.termQuery(SearchHistoryIndexConstant.FIELD_CONTENT,historySearchBackendQueryDTO.getContent()));
        }
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(boolQueryBuilder);
        PageRequest pageRequest = page(historySearchBackendQueryDTO);
        nativeSearchQuery.setPageable(pageRequest);
        SearchHits<SearchHistoryIndex> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, SearchHistoryIndex.class);
        if(searchHits.getTotalHits() <= 0){
            return new Page<>();
        }
        List<SearchHistoryIndex> contents = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(contents)) {
            return new Page<>();
        }
        List<HistorySearchBackendResultDTO> resultDTOList = contents.stream().map(index -> HistorySearchBackendResultDTO.builder()
                        .content(index.getContent()).createTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(index.getCreateTime()), ZoneOffset.ofHours(8))).build())
                .collect(Collectors.toList());
        return new Page<HistorySearchBackendResultDTO>(historySearchBackendQueryDTO.getCurrent(),historySearchBackendQueryDTO.getSize(),searchHits.getTotalHits()).setRecords(resultDTOList);
    }

    private List<? extends Terms.Bucket> sub(List<? extends Terms.Bucket> source, PopularSearchBackendQueryDTO popularBackendSearchDTO){
        if(popularBackendSearchDTO.getCurrent() == null){
            popularBackendSearchDTO.setCurrent(1);
        }
        if(popularBackendSearchDTO.getSize() == null){
            popularBackendSearchDTO.setSize(10);
        }
        int start = (popularBackendSearchDTO.getCurrent() - 1) * popularBackendSearchDTO.getSize();
        if(start >= source.size()){
            return Collections.emptyList();
        }
        int end = start + popularBackendSearchDTO.getSize();
        if(end > source.size()){
            end = source.size();
        }
        return source.subList(start,end);
    }

    private PageRequest page(HistorySearchBackendQueryDTO historySearchBackendQueryDTO){
        if(historySearchBackendQueryDTO.getCurrent() == null){
            historySearchBackendQueryDTO.setCurrent(1);
        }
        if(historySearchBackendQueryDTO.getSize() == null){
            historySearchBackendQueryDTO.setSize(10);
        }

        PageRequest pageRequest = PageRequest.of((historySearchBackendQueryDTO.getCurrent() - 1), historySearchBackendQueryDTO.getSize());
        return pageRequest.withSort(Sort.Direction.DESC,SearchHistoryIndexConstant.FIELD_CREATE_TIME);
    }

}
