package com.gejian.search.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：lijianghuai
 * @date ：2021-09-13 16:11
 * @description：
 */
@ApiModel("删除观看历史记录dto")
@Data
public class WatchHistoryDeleteDTO implements Serializable {

    @ApiModelProperty(value = "历史记录id",required = true)
    private Long historyId;

    @ApiModelProperty(value = "取值为ROOM/VIDEO",required = true)
    private String type;

}
