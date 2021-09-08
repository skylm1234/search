package com.gejian.search.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：lijianghuai
 * @date ：2021-09-07 18:15
 * @description：
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("热门搜索关键字")
public class PopularSearchBackendResultDTO {

    /**
     * 高频词
     */
    @ApiModelProperty("高频关键字")
    private String keyword;

    /**
     * 出现次数
     */
    @ApiModelProperty("出现次数")
    private Long count;
}
