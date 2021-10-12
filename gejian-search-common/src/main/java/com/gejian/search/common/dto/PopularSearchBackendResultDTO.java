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
@ApiModel("热搜词统计dto")
public class PopularSearchBackendResultDTO {

    /**
     * 热搜词
     */
    @ApiModelProperty("热搜词")
    private String keyword;

    /**
     * 搜索指数
     */
    @ApiModelProperty("搜索指数")
    private Long count;


    /**
     * 上榜状态
     */
    @ApiModelProperty("上榜状态")
    private Boolean ranked;
}
