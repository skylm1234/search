package com.gejian.search.web.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gejian.common.core.constant.SecurityConstants;
import com.gejian.common.minio.annotation.MinioResponse;
import com.gejian.common.security.service.GeJianUser;
import com.gejian.common.security.util.SecurityUtils;
import com.gejian.search.common.constant.WatchHistoryIndexConstant;
import com.gejian.search.common.dto.WatchHistoryDeleteDTO;
import com.gejian.search.common.dto.WatchHistoryQueryDTO;
import com.gejian.search.common.dto.WatchHistoryResponseDTO;
import com.gejian.search.common.enums.WatchTypeEnum;
import com.gejian.search.common.index.WatchHistoryIndex;
import com.gejian.search.web.service.WatchHistoryService;
import com.gejian.substance.client.dto.online.app.view.OnlineSearchDTO;
import com.gejian.substance.client.dto.playRecord.SubstancePlayRecordDeleteDTO;
import com.gejian.substance.client.feign.RemoteSubstanceService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author ：lijianghuai
 * @date ：2021-09-13 16:17
 * @description：
 */

@Service
public class WatchHistoryServiceImpl implements WatchHistoryService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private RemoteSubstanceService remoteSubstanceService;

    @Override
    public Page<OnlineSearchDTO> searchSubstance(WatchHistoryQueryDTO watchHistoryQueryDTO) {
        //TODO
        return new Page<>();
    }

    @Override
    public Page<OnlineSearchDTO> searchRoom(WatchHistoryQueryDTO watchHistoryQueryDTO) {
        //TODO
        return new Page<>();
    }

    @MinioResponse
    @Override
    public Page<WatchHistoryResponseDTO> search(WatchHistoryQueryDTO watchHistoryQueryDTO) {
        if(WatchTypeEnum.ROOM.name().equals(watchHistoryQueryDTO.getType())){
            return new Page<>();
        }
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        GeJianUser user = SecurityUtils.getUser();
        boolQueryBuilder.must(QueryBuilders.termQuery(WatchHistoryIndexConstant.FIELD_RECORD_TYPE,1));
        boolQueryBuilder.must(QueryBuilders.termQuery(WatchHistoryIndexConstant.FIELD_USER_ID,user.getId()));
        boolQueryBuilder.must(QueryBuilders.termQuery(WatchHistoryIndexConstant.FIELD_TYPE, WatchTypeEnum.VIDEO.name().toLowerCase()));
        boolQueryBuilder.must(QueryBuilders.termQuery(WatchHistoryIndexConstant.FIELD_DELETED, Boolean.FALSE));
        if(StringUtils.isNotBlank(watchHistoryQueryDTO.getTitle())){
            boolQueryBuilder.must(QueryBuilders.matchQuery(WatchHistoryIndexConstant.FIELD_TITLE,watchHistoryQueryDTO.getTitle()));
        }
        PageRequest pageRequest = PageRequest.of((watchHistoryQueryDTO.getCurrent() - 1) * watchHistoryQueryDTO.getSize(), watchHistoryQueryDTO.getSize(),Sort.by(Sort.Direction.DESC,WatchHistoryIndexConstant.FIELD_CREATE_TIME));
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(boolQueryBuilder);
        nativeSearchQuery.setPageable(pageRequest);
        SearchHits<WatchHistoryIndex> searchHits =  elasticsearchRestTemplate.search(nativeSearchQuery, WatchHistoryIndex.class);
        if(searchHits.isEmpty()){
            return new Page<>();
        }
        List<WatchHistoryResponseDTO> watchHistoryResponseDTOs = searchHits.stream().map(hit -> {
            WatchHistoryIndex watchHistoryIndex = hit.getContent();
            WatchHistoryResponseDTO watchHistoryResponseDTO = new WatchHistoryResponseDTO();
            watchHistoryResponseDTO.setCoverFileName(watchHistoryIndex.getCoverFileName());
            watchHistoryResponseDTO.setCoverBucketName(watchHistoryIndex.getCoverBucketName());
            watchHistoryResponseDTO.setAuthorUserId(watchHistoryIndex.getAuthorId());
            watchHistoryResponseDTO.setAuthorNickname(watchHistoryIndex.getAuthorNickName());
            watchHistoryResponseDTO.setAuthorUsername(watchHistoryIndex.getAuthorUsername());
            watchHistoryResponseDTO.setRoomId(watchHistoryIndex.getRoomId());
            watchHistoryResponseDTO.setTitle(watchHistoryIndex.getTitle());
            watchHistoryResponseDTO.setSubstanceId(watchHistoryIndex.getSubstanceId());
            watchHistoryResponseDTO.setHistoryId(watchHistoryIndex.getHistoryId());
            watchHistoryResponseDTO.setWatchedAt(watchHistoryIndex.getCreateTime());
            watchHistoryResponseDTO.setType(watchHistoryIndex.getType());
            return watchHistoryResponseDTO;
        }).collect(Collectors.toList());
        return new Page<WatchHistoryResponseDTO>(watchHistoryQueryDTO.getCurrent(),watchHistoryQueryDTO.getSize(),searchHits.getTotalHits()).setRecords(watchHistoryResponseDTOs);
    }

    @Override
    public void delete(WatchHistoryDeleteDTO watchHistoryDeleteDTO) {
        if(WatchTypeEnum.VIDEO.name().equalsIgnoreCase(watchHistoryDeleteDTO.getType())){
            SubstancePlayRecordDeleteDTO substancePlayRecordDeleteDTO = new SubstancePlayRecordDeleteDTO();
            substancePlayRecordDeleteDTO.setId(watchHistoryDeleteDTO.getHistoryId());
            remoteSubstanceService.deletePlay(substancePlayRecordDeleteDTO, SecurityConstants.FROM_IN);
            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
            boolQueryBuilder.must(QueryBuilders.termQuery(WatchHistoryIndexConstant.FIELD_HISTORY_ID,watchHistoryDeleteDTO.getHistoryId()));
            boolQueryBuilder.must(QueryBuilders.termQuery(WatchHistoryIndexConstant.FIELD_TYPE,watchHistoryDeleteDTO.getType()));
            NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(boolQueryBuilder);
             elasticsearchRestTemplate.delete(nativeSearchQuery, WatchHistoryIndex.class);
        }
    }
}
