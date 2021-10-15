package com.gejian.search.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yuanxue
 * @date 2021-10-11
 */
@Data
@ApiModel("热搜新增dto")
public class HotSearchDTO implements Serializable {

    @ApiModelProperty("话题排名")
    private Integer ranking;

    @ApiModelProperty("话题内容")
    @NotBlank(message = "话题内容不能为空")
    private String content;

    @ApiModelProperty("关联词段")
    private String[] associatedWord;

    @ApiModelProperty("标识   0-无，1-热，2-新，3-荐，4-商")
    @NotNull(message = "标识不能为空")
    private Integer marking;

    @ApiModelProperty("是否置顶")
    @NotNull(message = "是否置顶不能为空")
    private Boolean stick;

}
