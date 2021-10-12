package com.gejian.search.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gejian.common.minio.annotation.BucketName;
import com.gejian.common.minio.annotation.FileUrl;
import com.gejian.common.minio.annotation.ObjectName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : lijianghuai
 * @date : 2021/10/09 15:06
 * @description :
 */
@Data
@ApiModel("在线视频搜索返回结果")
public class SubstanceOnlineResponseDTO implements Serializable {
	/**
	 * 在线视频ID
	 */
	@ApiModelProperty(value = "视频ID")
	private Long id;

	/**
	 * 封面图片地址
	 */
	@ApiModelProperty(value = "封面图片的url")
	@FileUrl
	private String coverFileUrl;

	/**
	 * 上传封面图片名称
	 */
	@ApiModelProperty(value = "上传封面图片名称")
	@ObjectName
	@JsonIgnore
	private String coverFileName;

	/**
	 * 封面图片存储的桶
	 */
	@ApiModelProperty("上传封面图片存储的桶")
	@BucketName
	@JsonIgnore
	private String coverBucketName;

	/**
	 * 视频标题
	 */
	@ApiModelProperty(value = "视频标题")
	private String videoTitle;

	/**
	 * 视频简介
	 */
	@ApiModelProperty(value = "视频简介")
	private String videoIntroduce;

	/**
	 * 视频原件名称
	 */
	@ApiModelProperty("视频原件上传名称")
	@JsonIgnore
	private String videoFileNameOriginal;

	/**
	 * 视频原件存储桶
	 */
	@ApiModelProperty("视频原件存储桶")
	@JsonIgnore
	private String videoBucketNameOriginal;

	/**
	 * 视频原件长度
	 */
	@ApiModelProperty(value = "视频原件长度")
	private Long videoLength;

	/**
	 * 是否允许下载，0-不允许，1-允许
	 */
	@ApiModelProperty(value = "是否允许下载，0-不允许，1-允许")
	private Boolean download;

	/**
	 * 观看等级
	 */
	@ApiModelProperty(value = "观看等级")
	private String watchLevel;

	/**
	 * 分类ID
	 */
	@ApiModelProperty(value = "分类ID")
	private Long classifyId;

	/**
	 * 推送等级
	 */
	@ApiModelProperty(value = "推送等级")
	private String pushLevel;

	/**
	 * '是否下架，1-true-下架，0-false-正常
	 */
	@ApiModelProperty(value = "是否下架，1-true-下架，0-false-正常")
	private Boolean failure;

	/**
	 * 内容id
	 */
	@ApiModelProperty(value = "内容ID")
	private Long substanceId;

	/**
	 * 点击数量
	 */
	@ApiModelProperty(value = "点击数量")
	private Long clickCount;

	/**
	 * 播放数量
	 */
	@ApiModelProperty(value = "播放数量")
	private Long playCount;

	/**
	 * 完播数量
	 */
	@ApiModelProperty(value = "完播数量")
	private Long finishPlayCount;

	/**
	 * 评论数量
	 */
	@ApiModelProperty(value = "评论数量")
	private Long commentCount;

	/**
	 * 弹幕数量
	 */
	@ApiModelProperty(value = "弹幕数量")
	private Long bulletCount;

	/**
	 * 点赞数量
	 */
	@ApiModelProperty(value = "点赞数量")
	private Long likeCount;

	/**
	 * 转发数量
	 */
	@ApiModelProperty(value = "转发数量")
	private Long forwardCount;

	/**
	 * 下载数量
	 */
	@ApiModelProperty(value = "下载数量")
	private Long downloadCount;

	/**
	 * 收藏数量
	 */
	@ApiModelProperty(value = "收藏数量")
	private Long collectCount;

	/**
	 * 不感兴趣数量
	 */
	@ApiModelProperty(value = "不感兴趣数量")
	private Long unlikeCount;

	/**
	 * 分享数量
	 */
	@ApiModelProperty(value = "分享数量")
	private Long shareCount;

	/**
	 * 举报数量
	 */
	@ApiModelProperty(value = "举报数量")
	private Long accusationCount;

	/**
	 * 反馈数量
	 */
	@ApiModelProperty(value = "反馈数量")
	private Long feedbackCount;

	/**
	 * 创建用户id
	 */
	@ApiModelProperty(value = "创建用户id")
	private Long createUserId;

	/**
	 * 创建用户姓名
	 */
	@ApiModelProperty(value = "创建用户姓名")
	private String createUserName;

	/**
	 * 创建用户昵称
	 */
	@ApiModelProperty(value = "创建用户昵称")
	private String createUserNikeName;

}
