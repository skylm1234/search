package com.gejian.search.common.dto;

import com.gejian.common.core.util.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：lijianghuai
 * @date ：2021-09-13 16:11
 * @description：
 */
@ApiModel("观看记录搜索dto")
@Data
public class WatchHistoryQueryDTO extends BasePageQuery {


    @ApiModelProperty(value = "取值为ROOM/VIDEO,不传默认所有")
    private String type;


    @ApiModelProperty(value = "直播间/视频 标题")
    private String title;

}
