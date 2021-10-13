package com.gejian.search.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gejian.search.common.constant.HotSearchIndexConstant;
import com.gejian.search.common.dto.*;
import com.gejian.search.common.enums.HotSearchTypeEnum;
import com.gejian.search.common.index.HotSearchIndex;
import com.gejian.search.web.service.HotSearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author ：yuanxue
 * @date ：2021-10-11
 * @description：热搜
 */
@Component
@Slf4j
public class HotSearchServiceImpl implements HotSearchService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public Boolean saveHotSearch(HotSearchDTO hotSearchDTO) {
        try {
            //新增话题是否需要置顶
            if (hotSearchDTO.getStick()){
                //需要则将之前置顶的话题放置对应位置
                getStickHotSearch(null, true);
            } else {
                //不需要则将新增话题排名后的依次顺延
                updateReduceRanking(hotSearchDTO.getRanking(), HotSearchIndexConstant.MAX_RANKING);
            }
            HotSearchIndex hotSearchIndex = HotSearchIndex.builder()
                    .content(hotSearchDTO.getContent())
                    .associatedWord(hotSearchDTO.getAssociatedWord())
                    .marking(HotSearchTypeEnum.getType(hotSearchDTO.getMarking()).getType())
                    .ranking(hotSearchDTO.getRanking())
                    .stick(hotSearchDTO.getStick())
                    .createTime(LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8)))
                    .deleted(false)
                    .build();
            elasticsearchRestTemplate.save(hotSearchIndex);
            return true;
        } catch (Exception e){
            log.error("热搜话题保存失败！", e);
            return false;
        }
    }

    @Override
    public Boolean updateHotSearch(HotSearchUpdateDTO hotSearchDTO) {
        try {
            HotSearchIndex search = elasticsearchRestTemplate.get(hotSearchDTO.getId(), HotSearchIndex.class);
            Asserts.notNull(search, hotSearchDTO.getId() + "话题不存在");
            Document document = Document.create();
            if (Objects.nonNull(hotSearchDTO.getStick())) {
                if (hotSearchDTO.getStick()) {
                    getStickHotSearch(search.getRanking(), false);
                } else {
                    updateReduceRanking(search.getRanking(), HotSearchIndexConstant.MAX_RANKING);
                }
                document.putIfAbsent(HotSearchIndexConstant.FIELD_STICK, hotSearchDTO.getStick());
            } else if (Objects.nonNull(hotSearchDTO.getRanking()) && !search.getStick()) {
                if (search.getRanking() > hotSearchDTO.getRanking()) {
                    updateReduceRanking(hotSearchDTO.getRanking(), search.getRanking());
                } else {
                    updateIncreaseRanking(search.getRanking(), hotSearchDTO.getRanking());
                }
            }
            if (Objects.nonNull(hotSearchDTO.getContent())) {
                document.putIfAbsent(HotSearchIndexConstant.FIELD_CONTENT, hotSearchDTO.getContent());
            }
            if (Objects.nonNull(hotSearchDTO.getAssociatedWord())) {
                document.putIfAbsent(HotSearchIndexConstant.FIELD_ASSOCIATED_WORD, hotSearchDTO.getAssociatedWord());
            }
            if (Objects.nonNull(hotSearchDTO.getMarking())) {
                document.putIfAbsent(HotSearchIndexConstant.FIELD_MARKING, HotSearchTypeEnum.getType(hotSearchDTO.getMarking()).getType());
            }
            if (Objects.nonNull(hotSearchDTO.getRanking())) {
                document.putIfAbsent(HotSearchIndexConstant.FIELD_RANKING, hotSearchDTO.getRanking());
            }
            document.setId(hotSearchDTO.getId());
            UpdateQuery build = UpdateQuery.builder(hotSearchDTO.getId()).withDocument(document).withScriptedUpsert(true).build();
            elasticsearchRestTemplate.update(build, IndexCoordinates.of(HotSearchIndexConstant.INDEX_NAME));
            return true;
        } catch (Exception e){
            log.error(hotSearchDTO.getId() + "热搜修改失败！", e);
            return false;
        }
    }

    @Override
    public Boolean deleteHotSearch(HotSearchDeleteDTO hotSearchDeleteDTO) {
        try {
            if (!hotSearchDeleteDTO.getStick()) {
                updateIncreaseRanking(hotSearchDeleteDTO.getRanking(), HotSearchIndexConstant.MAX_RANKING);
            }
            deleteTopic(HotSearchIndexConstant.FIELD_DELETED, hotSearchDeleteDTO.getId());
            return true;
        } catch (Exception e){
            log.error(hotSearchDeleteDTO.getId() + "热搜删除失败！", e);
            return false;
        }
    }

    /**
     * 删除话题
     * @param fieldDeleted
     * @param id
     */
    private void deleteTopic(String fieldDeleted, String id) {
        Document document = Document.create();
        document.putIfAbsent(fieldDeleted, true);
        document.setId(id);
        UpdateQuery build = UpdateQuery.builder(id).withDocument(document).withScriptedUpsert(true).build();
        elasticsearchRestTemplate.update(build, IndexCoordinates.of(HotSearchIndexConstant.INDEX_NAME));
    }

    @Override
    public Boolean stickHotSearch(HotSearchStickDTO hotSearchStickDTO) {
        try {
            getStickHotSearch(hotSearchStickDTO.getRanking(), false);
            Document document = Document.create();
            document.putIfAbsent(HotSearchIndexConstant.FIELD_STICK, true);
            document.putIfAbsent(HotSearchIndexConstant.FIELD_RANKING, hotSearchStickDTO.getRanking());
            document.setId(hotSearchStickDTO.getId());
            UpdateQuery build = UpdateQuery.builder(hotSearchStickDTO.getId()).withDocument(document).withScriptedUpsert(true).build();
            elasticsearchRestTemplate.update(build, IndexCoordinates.of(HotSearchIndexConstant.INDEX_NAME));
            return true;
        } catch (Exception e){
            log.error(hotSearchStickDTO.getId() + "热搜置顶失败！", e);
            return false;
        }
    }

    /**
     * 查询是否存在置顶话题，若存在就放置于对应排名处,并修改排名
     * @param ranking
     * @param isSave
     */
    private void getStickHotSearch(Integer ranking, Boolean isSave) {
        //查询是否存在置顶话题
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.termQuery(HotSearchIndexConstant.FIELD_DELETED, false));
        boolQueryBuilder.must(QueryBuilders.termQuery(HotSearchIndexConstant.FIELD_STICK, true));
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();
        SearchHits<HotSearchIndex> search = elasticsearchRestTemplate.search(searchQuery, HotSearchIndex.class);
        if (search.getTotalHits() > 0) {
            Document document = Document.create();
            //若存在旧置顶话题
            if (Objects.isNull(search.getSearchHit(0).getContent().getRanking())){
                //旧置顶话题不存在排名值
                document.putIfAbsent(HotSearchIndexConstant.FIELD_DELETED, true);
                updateIncreaseRanking(ranking, HotSearchIndexConstant.MAX_RANKING);
            } else {
                if (Objects.isNull(ranking)){
                    ranking = HotSearchIndexConstant.MAX_RANKING;
                }
                //旧置顶话题存在排名值
                if (search.getSearchHit(0).getContent().getRanking() < ranking) {
                    updateReduceRanking(search.getSearchHit(0).getContent().getRanking(), ranking);
                } else {
                    updateIncreaseRanking(search.getSearchHit(0).getContent().getRanking(), ranking);
                }
            }
            document.putIfAbsent(HotSearchIndexConstant.FIELD_STICK, false);
            document.setId(search.getSearchHit(0).getContent().getId());
            UpdateQuery build = UpdateQuery.builder(search.getSearchHit(0).getContent().getId()).withDocument(document).withScriptedUpsert(true).build();
            elasticsearchRestTemplate.update(build, IndexCoordinates.of(HotSearchIndexConstant.INDEX_NAME));
        } else if (!isSave){
            //不存在旧的置顶话题
            updateIncreaseRanking(ranking, HotSearchIndexConstant.MAX_RANKING);
        }
    }

    @Override
    public IPage<HotSearchResponseDTO> pageHotSearch(HotSearchQueryDTO hotSearchQueryDTO) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.termQuery(HotSearchIndexConstant.FIELD_DELETED, false));
        if (Objects.nonNull(hotSearchQueryDTO.getContent())){
            boolQueryBuilder.must(QueryBuilders.matchQuery(HotSearchIndexConstant.FIELD_CONTENT, hotSearchQueryDTO.getContent()));
        }
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(PageRequest.of(hotSearchQueryDTO.getCurrent() - 1, hotSearchQueryDTO.getSize()))
                .withSort(SortBuilders.fieldSort(HotSearchIndexConstant.FIELD_STICK).order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort(HotSearchIndexConstant.FIELD_RANKING).order(SortOrder.ASC))
                .build();
        SearchHits<HotSearchIndex> search = elasticsearchRestTemplate.search(searchQuery, HotSearchIndex.class);

        List<HotSearchIndex> contents = search.stream().map(SearchHit::getContent).collect(Collectors.toList());
        if (search.getTotalHits() <= 0 || CollectionUtils.isEmpty(contents)) {
            return new Page<>();
        }
        return new Page<HotSearchResponseDTO>(hotSearchQueryDTO.getCurrent(), hotSearchQueryDTO.getSize(),
                search.getTotalHits()).setRecords(BeanUtil.copyToList(contents, HotSearchResponseDTO.class));
    }

    @Override
    public List<String> getHotSearchList(Integer size) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.termQuery(HotSearchIndexConstant.FIELD_DELETED, false));
        boolQueryBuilder.must(QueryBuilders.termQuery(HotSearchIndexConstant.FIELD_STICK, false));
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(PageRequest.of(0, size))
                .withSort(SortBuilders.fieldSort(HotSearchIndexConstant.FIELD_RANKING).order(SortOrder.ASC))
                .build();
        SearchHits<HotSearchIndex> search = elasticsearchRestTemplate.search(searchQuery, HotSearchIndex.class);
        if (search.getTotalHits() <= 0){
            return new ArrayList<>() ;
        }
        List<HotSearchIndex> contents = search.stream().map(SearchHit::getContent).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(contents)) {
            return new ArrayList<>() ;
        }
        return contents.stream().map(HotSearchIndex::getContent).collect(Collectors.toList());
    }

    @Override
    public List<HotSearchIndex> findAll() {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termQuery(HotSearchIndexConstant.FIELD_DELETED, false)).build();
        SearchHits<HotSearchIndex> searchHits = elasticsearchRestTemplate.search(searchQuery, HotSearchIndex.class);
        return searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    /**
     * 将排名低于等于ranking的话题排名依次降低一位
     * @param ranking
     */
    private void updateReduceRanking(Integer ranking, Integer ending){
        if (Objects.isNull(ranking) || Objects.isNull(ending)){
            return;
        }
        //查询ranking位置是否有数据
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.termQuery(HotSearchIndexConstant.FIELD_STICK, false));
        boolQueryBuilder.must(QueryBuilders.termQuery(HotSearchIndexConstant.FIELD_DELETED, false));
        boolQueryBuilder.must(QueryBuilders.rangeQuery(HotSearchIndexConstant.FIELD_RANKING).gte(ranking).lt(ending));
        NativeSearchQuery searchQuery = new NativeSearchQuery(boolQueryBuilder);
        SearchHits<HotSearchIndex> search = elasticsearchRestTemplate.search(searchQuery, HotSearchIndex.class);

        //判断是否有数据
        if (search.getTotalHits() > 0){
            int[] rankings = search.stream().map(SearchHit::getContent).mapToInt(HotSearchIndex::getRanking).sorted().toArray();
            int index = 1;
            for (int i = 1; i < rankings.length ; i++) {
                if (rankings[i] - 1 != rankings[i - 1]){
                    break;
                }
                index += i;
            }
            List<HotSearchIndex> contents = search.stream().map(SearchHit::getContent).collect(Collectors.toList()).subList(0, index);

            if (contents.size() > 0) {
                List<UpdateQuery> updateQueryList = new ArrayList<>(contents.size());
                contents.forEach(hotSearchIndex -> {
                    if (Objects.equals(hotSearchIndex.getRanking(), HotSearchIndexConstant.MAX_RANKING)){
                        deleteTopic(HotSearchIndexConstant.FIELD_DELETED, hotSearchIndex.getId());
                    } else {
                        Document document = Document.create();
                        document.putIfAbsent(HotSearchIndexConstant.FIELD_RANKING, hotSearchIndex.getRanking() + 1);
                        document.setId(hotSearchIndex.getId());
                        UpdateQuery build = UpdateQuery.builder(hotSearchIndex.getId()).withDocument(document).withScriptedUpsert(true).build();
                        updateQueryList.add(build);
                    }
                });

                //将排名后移一位
                elasticsearchRestTemplate.bulkUpdate(updateQueryList, IndexCoordinates.of(HotSearchIndexConstant.INDEX_NAME));
            }
        }


    }

    /**
     * 将排名低于ranking的话题排名依次提升一位
     * @param ranking
     */
    private void updateIncreaseRanking(Integer ranking, Integer ending){
        if (Objects.isNull(ranking) || Objects.isNull(ending)){
            return;
        }
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.termQuery(HotSearchIndexConstant.FIELD_DELETED, false));
        boolQueryBuilder.must(QueryBuilders.termQuery(HotSearchIndexConstant.FIELD_STICK, false));
        boolQueryBuilder.must(QueryBuilders.rangeQuery(HotSearchIndexConstant.FIELD_RANKING).gt(ranking).lte(ending));
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withSort(SortBuilders.fieldSort(HotSearchIndexConstant.FIELD_RANKING).order(SortOrder.ASC))
                .build();
        SearchHits<HotSearchIndex> search = elasticsearchRestTemplate.search(searchQuery, HotSearchIndex.class);
        if (search.getTotalHits() > 0) {
            List<HotSearchIndex> hotSearchList = search.stream().map(SearchHit::getContent).collect(Collectors.toList());
            if (hotSearchList.size() > 0) {
                List<UpdateQuery> updateQueryList = new ArrayList<>(hotSearchList.size());
                hotSearchList.forEach(hotSearchIndex -> {
                    Document document = Document.create();
                    document.putIfAbsent(HotSearchIndexConstant.FIELD_RANKING, hotSearchIndex.getRanking() - 1);
                    document.setId(hotSearchIndex.getId());
                    UpdateQuery build = UpdateQuery.builder(hotSearchIndex.getId()).withDocument(document).withScriptedUpsert(true).build();
                    updateQueryList.add(build);
                });

                //将排名前移一位
                elasticsearchRestTemplate.bulkUpdate(updateQueryList, IndexCoordinates.of(HotSearchIndexConstant.INDEX_NAME));
            }
        }
    }

}

