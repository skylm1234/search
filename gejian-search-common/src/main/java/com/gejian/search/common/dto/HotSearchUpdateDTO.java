package com.gejian.search.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author yuanxue
 * @date 2021-10-11
 */
@Data
@ApiModel("热搜编辑dto")
public class HotSearchUpdateDTO implements Serializable {

    @ApiModelProperty("id")
    @NotBlank(message = "话题id不能为空")
    private String id;

    @ApiModelProperty("话题排名")
    private Integer ranking;

    @ApiModelProperty("话题内容")
    private String content;

    @ApiModelProperty("关联词段")
    private String[] associatedWord;

    @ApiModelProperty("标识")
    private Integer  marking;

    @ApiModelProperty("是否置顶")
    private Boolean stick;

}
