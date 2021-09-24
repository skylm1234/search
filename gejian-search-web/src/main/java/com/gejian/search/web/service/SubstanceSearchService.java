package com.gejian.search.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gejian.common.security.service.GeJianUser;
import com.gejian.search.common.dto.SubstanceSearchDTO;
import com.gejian.search.common.dto.UserSearchDTO;
import com.gejian.substance.client.dto.online.app.view.OnlineSearchDTO;
import com.gejian.substance.client.dto.video.UserSearchVideoViewDTO;

import java.util.List;

/**
 * @author ：lijianghuai
 * @date ：2021-08-25 10:17
 * @description：
 */
public interface SubstanceSearchService {

    /**
     * 搜索在线视频
     *
     * @param substanceSearchDTO
     * @return
     */
    Page<OnlineSearchDTO> search(SubstanceSearchDTO substanceSearchDTO);

    /**
     * 搜搜用户视频
     *
     * @param userSearchDTO
     * @return
     */
    List<UserSearchVideoViewDTO> searchUserVideo(UserSearchDTO userSearchDTO, GeJianUser geJianUser);


}
