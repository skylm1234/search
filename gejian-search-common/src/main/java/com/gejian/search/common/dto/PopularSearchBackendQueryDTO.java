package com.gejian.search.common.dto;

import com.gejian.common.core.util.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lijianghuai
 * @date 2021-09-08
 */
@Data
@ApiModel("管理后台查询热搜词统计dto")
public class PopularSearchBackendQueryDTO extends BasePageQuery {

    @ApiModelProperty("统计时间-开始")
    private LocalDateTime startedAt;

    @ApiModelProperty("统计时间-结束")
    private LocalDateTime terminatedAt;

    @ApiModelProperty("热搜词")
    private String content;
}
