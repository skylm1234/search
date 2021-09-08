package com.gejian.search.common.dto;

import lombok.Data;

/**
 * @author ：lijianghuai
 * @date ：2021-09-07 18:15
 * @description：
 */

@Data
public class PopularResultDTO {

    /**
     * 高频词
     */
    private String keyword;

    /**
     * 出现次数
     */
    private int count;
}
