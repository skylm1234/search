package com.gejian.search.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author ：lijianghuai
 * @date ：2021-09-07 18:15
 * @description：
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("历史搜索返回DTO")
public class HistorySearchBackendResultDTO {

    /**
     * 高频词
     */
    @ApiModelProperty("搜索内容")
    private String content;

    /**
     * 搜索时间
     */
    @ApiModelProperty("搜索时间")
    private LocalDateTime createTime;
}
