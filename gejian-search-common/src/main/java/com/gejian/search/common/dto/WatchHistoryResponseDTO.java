package com.gejian.search.common.dto;

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
    private LocalDateTime createTime;


    @ApiModelProperty("视频/直播 更新时间")
    private LocalDateTime updateTime;


    @ApiModelProperty("视频/直播 封面")
    @FileUrl
    private String coverFileUrl;

    @ObjectName
    private String coverFileName;

    @BucketName
    private String coverBucketName;

}
