package com.gejian.search.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gejian.search.common.dto.*;
import com.gejian.search.common.index.HotSearchIndex;

import java.util.List;

/**
 * @author yuanxue
 * @Description 热搜
 * @createTime 2021年10月11日 11:11:00
 */
public interface HotSearchService {
    /**
     * 保存热搜话题
     * @param hotSearchDTO 话题dto
     */
    Boolean saveHotSearch(HotSearchDTO hotSearchDTO);

    /**
     * 修改热搜话题
     * @param hotSearchDTO 话题dto
     */
    Boolean updateHotSearch(HotSearchUpdateDTO hotSearchDTO);

    /**
     * 删除热搜话题
     * @param hotSearchDeleteDTO 删除dto
     */
    Boolean deleteHotSearch(HotSearchDeleteDTO hotSearchDeleteDTO);

    /**
     * 置顶热搜话题
     * @param hotSearchStickDTO 置顶dto
     */
    Boolean stickHotSearch(HotSearchStickDTO hotSearchStickDTO);

    /**
     * 分页查询热搜话题
     * @param hotSearchQueryDTO 查询条件dto
     * @return R
     */
    IPage<HotSearchResponseDTO> pageHotSearch(HotSearchQueryDTO hotSearchQueryDTO);

    /**
     * app热搜词
     * @param size
     * @return
     */
    List<String> getHotSearchList(Integer size);

    List<HotSearchIndex> findAll();
}
