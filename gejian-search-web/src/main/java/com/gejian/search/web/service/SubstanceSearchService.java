package com.gejian.search.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gejian.search.common.dto.SubstanceSearchDTO;
import com.gejian.substance.client.dto.online.SubstanceOnlineAndCountDTO;

/**
 * @author ：lijianghuai
 * @date ：2021-08-25 10:17
 * @description：
 */
public interface SubstanceSearchService {

    Page<SubstanceOnlineAndCountDTO> search(SubstanceSearchDTO substanceSearchDTO);

}
