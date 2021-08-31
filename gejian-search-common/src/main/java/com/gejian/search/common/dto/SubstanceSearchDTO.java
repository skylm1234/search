package com.gejian.search.common.dto;

import com.gejian.search.common.enums.SearchOrderFieldsEnum;
import com.gejian.search.common.enums.SearchTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：lijianghuai
 * @date ：2021-08-23 17:54
 * @description：
 */
@ApiModel("视频搜索DTO")
@Data
public class SubstanceSearchDTO {

	@ApiModelProperty(value = "搜索类型;综合/游戏都为video，用户为user，默认为video")
	private SearchTypeEnum searchType;

	@ApiModelProperty(value = "搜索正文",required = true,name = "content")
	private String content;

	@ApiModelProperty(value = "分类id")
	private Long classifyId;

	@ApiModelProperty(value = "视频时长大于等于")
	private Integer videoLengthGt;

	@ApiModelProperty(value = "视频时长小于等于")
	private Integer videoLengthLt;

	@ApiModelProperty(value = "排序字段,default(默认)，newest(最新)，play_most(播放)，comment_most(评论)，bullet_most(弹幕)，collect_most(收藏)，forward_most(转发)")
	private SearchOrderFieldsEnum orderField;

	@ApiModelProperty(value = "当前页，默认为1", name = "current")
	private Integer current;

	@ApiModelProperty(value = "每页条数，默认为10", name = "size")
	private Integer size;

}
