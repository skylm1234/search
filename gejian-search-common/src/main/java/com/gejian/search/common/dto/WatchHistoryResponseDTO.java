package com.gejian.search.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gejian.common.minio.annotation.BucketName;
import com.gejian.common.minio.annotation.FileUrl;
import com.gejian.common.minio.annotation.ObjectName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ：lijianghuai
 * @date ：2021-09-29 14:21
 * @description：
 */

@Data
@ApiModel("观看历史返回数据")
public class WatchHistoryResponseDTO {

    @ApiModelProperty("视频id，如果返回结果是视频，则有")
    private Long substanceId;

    @ApiModelProperty("直播间id，如果返回结果是直播，则有")
    private Long roomId;

    @ApiModelProperty("视频/直播 标题")
    private String title;

    @ApiModelProperty("视频/直播 用户名")
    private String authorUsername;

    @ApiModelProperty("视频/直播 昵称")
    private String authorNickname;

    @ApiModelProperty("视频/直播 用户id")
    private Long authorUserId;

    @ApiModelProperty("视频/直播 观看时间")
    private LocalDateTime watchedAt;

    @ApiModelProperty("类型，ROOM:直播;VIDEO:视频 ")
    private String type;

    @ApiModelProperty("视频/直播 封面url")
    @FileUrl
    private String coverFileUrl;

    @ApiModelProperty("观看等级")
    private String watchLevel;

    @ApiModelProperty("视频/直播 封面文件名")
    @ObjectName
    @JsonIgnore
    private String coverFileName;

    @ApiModelProperty("视频/直播 封面桶名")
    @BucketName
    @JsonIgnore
    private String coverBucketName;

    @ApiModelProperty("历史记录id")
    private Long historyId;

    @ApiModelProperty("是否开播；0：未开播，1：已开播")
    private Integer streamStatus;

    @ApiModelProperty("观看毫秒数")
    private Long seenMs;
}
