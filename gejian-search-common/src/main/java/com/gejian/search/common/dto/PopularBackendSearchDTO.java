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
@ApiModel("管理后台查询dto")
public class PopularBackendSearchDTO extends BasePageQuery {

    @ApiModelProperty("开始时间")
    private LocalDateTime startedAt;

    @ApiModelProperty("结束时间")
    private LocalDateTime terminatedAt;

    @ApiModelProperty("搜索文本")
    private String content;
}
