package com.gejian.search.common.dto;

import com.gejian.common.core.util.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author lijianghuai
 * @date 2021-09-08
 */
@Data
@ApiModel("管理后台查询热搜词统计dto")
public class PopularSearchBackendQueryDTO extends BasePageQuery {

    @ApiModelProperty(value = "统计时间-开始",example = "2021-10-01")
    private LocalDate startedAt;

    @ApiModelProperty(value = "统计时间-结束",example = "2021-10-10")
    private LocalDate terminatedAt;

    @ApiModelProperty("热搜词")
    private String content;
}
