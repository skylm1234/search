package com.gejian.search.web.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gejian.common.core.constant.SecurityConstants;
import com.gejian.common.core.util.R;
import com.gejian.common.security.service.GeJianUser;
import com.gejian.common.security.util.SecurityUtils;
import com.gejian.search.common.constant.WatchHistoryIndexConstant;
import com.gejian.search.common.dto.WatchHistoryQueryDTO;
import com.gejian.search.common.enums.WatchTypeEnum;
import com.gejian.search.common.index.WatchHistoryIndex;
import com.gejian.search.web.service.WatchHistoryService;
import com.gejian.substance.client.dto.online.app.view.OnlineSearchDTO;
import com.gejian.substance.client.dto.online.rpc.RpcOnlineSearchDTO;
import com.gejian.substance.client.feign.RemoteSubstanceService;
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
        SearchHits<WatchHistoryIndex> searchHits = search(watchHistoryQueryDTO);
        if(searchHits.isEmpty()){
            return new Page<>();
        }
        RpcOnlineSearchDTO rpcOnlineSearchDTO = new RpcOnlineSearchDTO();
        rpcOnlineSearchDTO.setIds(searchHits.stream().map(hit -> hit.getContent().getSubstanceId()).collect(Collectors.toList()));
        final R<List<OnlineSearchDTO>> listR = remoteSubstanceService.search(rpcOnlineSearchDTO, SecurityConstants.FROM_IN);
        return new Page<OnlineSearchDTO>(watchHistoryQueryDTO.getCurrent(),watchHistoryQueryDTO.getSize(),searchHits.getTotalHits()).setRecords(listR.getData());
    }

    @Override
    public Page<OnlineSearchDTO> searchRoom(WatchHistoryQueryDTO watchHistoryQueryDTO) {
        //TODO
        return new Page<>();
    }

    private SearchHits<WatchHistoryIndex> search(WatchHistoryQueryDTO watchHistoryQueryDTO){
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        GeJianUser user = SecurityUtils.getUser();
        boolQueryBuilder.must(QueryBuilders.termQuery(WatchHistoryIndexConstant.FIELD_USER_ID,user.getId()));
        boolQueryBuilder.must(QueryBuilders.termQuery(WatchHistoryIndexConstant.FIELD_TYPE, WatchTypeEnum.VIDEO.name()));
        boolQueryBuilder.must(QueryBuilders.termQuery(WatchHistoryIndexConstant.FIELD_DELETED, Boolean.FALSE));
        PageRequest pageRequest = PageRequest.of((watchHistoryQueryDTO.getCurrent() - 1) * watchHistoryQueryDTO.getSize(), watchHistoryQueryDTO.getSize(),Sort.by(Sort.Direction.DESC,WatchHistoryIndexConstant.FIELD_CREATE_TIME));
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(boolQueryBuilder);
        nativeSearchQuery.setPageable(pageRequest);
        return elasticsearchRestTemplate.search(nativeSearchQuery, WatchHistoryIndex.class);
    }
}
