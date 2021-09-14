package com.gejian.search.common.dto;

import com.gejian.common.core.util.BasePageQuery;
import com.gejian.search.common.enums.WatchTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ：lijianghuai
 * @date ：2021-09-13 16:11
 * @description：
 */
@ApiModel("观看记录搜索dto")
@Data
public class WatchHistoryQueryDTO extends BasePageQuery {

    @NotNull
    @ApiModelProperty("观看类型，VIDEO: 视频; ROOM:直播")
    private WatchTypeEnum watchTypeEnum;
}
