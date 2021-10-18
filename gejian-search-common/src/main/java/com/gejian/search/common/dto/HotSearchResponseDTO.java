package com.gejian.search.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yuanxue
 * @date 2021-10-11
 */
@Data
@ApiModel("热搜dto")
@Builder
public class HotSearchResponseDTO implements Serializable {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("话题排名")
    private Integer ranking;

    @ApiModelProperty("话题内容")
    private String content;

    @ApiModelProperty("关联词段")
    private String[] associatedWord;

    @ApiModelProperty("标识名字")
    private String marking;

    @ApiModelProperty("是否置顶")
    private Boolean stick;

}
