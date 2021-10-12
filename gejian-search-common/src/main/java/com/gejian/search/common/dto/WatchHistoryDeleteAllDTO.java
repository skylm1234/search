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
@ApiModel("删除全部观看历史记录dto")
@Data
public class WatchHistoryDeleteAllDTO implements Serializable {

    @ApiModelProperty(value = "取值为ROOM/VIDEO，不传为删除所有")
    private String type;

}
