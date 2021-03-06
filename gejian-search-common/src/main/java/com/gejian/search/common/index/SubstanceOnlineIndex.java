package com.gejian.search.common.index;

import com.gejian.search.common.constant.BasicConstant;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_BULLET_COUNT;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_CLASSIFY_ID;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_COLLECT_COUNT;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_COMMENT_COUNT;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_COVER_BUCKET_NAME;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_COVER_FILE_NAME;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_CREATE_TIME;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_CREATE_USER_ID;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_CREATE_USER_NAME;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_CREATE_USER_NICKNAME;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_DELETED;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_DOWNLOAD;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_FAILURE;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_FORWARD_COUNT;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_PLAY_COUNT;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_PRE_ID;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_PUSH_LEVEL;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_SHARE_COUNT;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_UPDATE_TIME;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_VIDEO_BUCKET_NAME_ORIGINAL;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_VIDEO_FILE_NAME_ORIGINAL;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_VIDEO_INTRODUCE;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_VIDEO_LENGTH;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_VIDEO_TITLE;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_WATCH_LEVEL;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.INDEX_NAME;

/**
 * @author ???lijianghuai
 * @date ???2021-08-23 17:29
 * @description??????????????????
 */

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Document(indexName = INDEX_NAME)
public class SubstanceOnlineIndex {
	@Id
	private Long id;

	@Field(type = FieldType.Text,name = FIELD_VIDEO_TITLE,analyzer = BasicConstant.IK_MAX_WORD)
	private String videoTitle;

	@Field(type = FieldType.Text,name = FIELD_VIDEO_INTRODUCE,analyzer = BasicConstant.IK_MAX_WORD)
	private String videoIntroduce;

	@Field(type = FieldType.Long,name = FIELD_VIDEO_LENGTH)
	private long videoLength;

	@Field(type = FieldType.Long,name = FIELD_CLASSIFY_ID)
	private long classifyId;

	@Field(type = FieldType.Long,name = FIELD_PRE_ID)
	private long substanceId;

	@Field(type = FieldType.Long,name = FIELD_CREATE_USER_ID)
	private long createUserId;

	@Field(type = FieldType.Keyword,name = FIELD_CREATE_USER_NAME)
	private String createUserName;

	@Field(type = FieldType.Text,name = FIELD_CREATE_USER_NICKNAME,analyzer = BasicConstant.IK_MAX_WORD)
	private String createUserNikeName;

	@Field(type = FieldType.Keyword,name = FIELD_COVER_FILE_NAME)
	private String coverFileName;

	@Field(type = FieldType.Keyword,name = FIELD_COVER_BUCKET_NAME)
	private String coverBucketName;

	@Field(type = FieldType.Keyword,name = FIELD_VIDEO_FILE_NAME_ORIGINAL)
	private String videoFileNameOriginal;

	@Field(type = FieldType.Keyword,name = FIELD_VIDEO_BUCKET_NAME_ORIGINAL)
	private String videoBucketNameOriginal;

	@Field(type = FieldType.Keyword,name = FIELD_PUSH_LEVEL)
	private String pushLevel;

	@Field(type = FieldType.Keyword,name = FIELD_WATCH_LEVEL)
	private String watchLevel;

	@Field(type = FieldType.Long,name = FIELD_PLAY_COUNT)
	private long playCount;

	@Field(type = FieldType.Long,name = FIELD_COMMENT_COUNT)
	private long commentCount;

	@Field(type = FieldType.Long,name = FIELD_BULLET_COUNT)
	private long bulletCount;

	@Field(type = FieldType.Long,name = FIELD_COLLECT_COUNT)
	private long collectCount;

	@Field(type = FieldType.Long,name = FIELD_FORWARD_COUNT)
	private long forwardCount;

	@Field(type = FieldType.Long,name = FIELD_SHARE_COUNT)
	private long shareCount;

	@Field(type = FieldType.Boolean,name = FIELD_DELETED)
	private boolean deleted;

	@Field(type = FieldType.Boolean,name = FIELD_DOWNLOAD)
	private boolean download;

	@Field(type = FieldType.Boolean,name = FIELD_FAILURE)
	private boolean failure;

	@Field(type = FieldType.Date,name = FIELD_CREATE_TIME,format = { DateFormat.date_time_no_millis, DateFormat.epoch_millis,DateFormat.epoch_second })
	private LocalDateTime createTime;

	@Field(type = FieldType.Date,name = FIELD_UPDATE_TIME,format = { DateFormat.date_time_no_millis, DateFormat.epoch_millis,DateFormat.epoch_second })
	private LocalDateTime updateTime;

}
