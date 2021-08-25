package com.gejian.search.web.service;

import com.gejian.search.common.dto.SubstanceSearchDTO;
import com.gejian.search.common.index.SubstanceOnlineIndex;
import org.springframework.data.domain.Page;

/**
 * @author ：lijianghuai
 * @date ：2021-08-25 10:17
 * @description：
 */
public interface SubstanceSearchService {

    Page<SubstanceOnlineIndex> search(SubstanceSearchDTO substanceSearchDTO);

}
