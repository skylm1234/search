package com.gejian.search.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author ：yuanxue
 * @date ：2021-10-11
 * @description：
 */
@ApiModel("置顶热搜记录dto")
@Data
public class HotSearchStickDTO implements Serializable {

    @ApiModelProperty(value = "话题id",required = true)
    @NotNull(message = "话题id不能为空")
    private String id;

    @ApiModelProperty("话题排名")
    @NotNull(message = "话题排名不能为空")
    private Integer ranking;
}
