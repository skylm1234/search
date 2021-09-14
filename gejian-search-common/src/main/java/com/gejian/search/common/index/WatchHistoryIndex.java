package com.gejian.search.common.index;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import static com.gejian.search.common.constant.WatchHistoryIndexConstant.*;

/**
 * @author ：lijianghuai
 * @date ：2021-09-13 15:36
 * @description：
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document(indexName = INDEX_NAME)
@Builder
public class WatchHistoryIndex {

    @Id
    private String id;

    @Field(type = FieldType.Keyword,name = FIELD_TYPE)
    private String type;

    @Field(type = FieldType.Date,name = FIELD_CREATE_TIME,format = { DateFormat.date_optional_time, DateFormat.epoch_millis,DateFormat.epoch_second })
    private Long createTime;

    @Field(type = FieldType.Date,name = FIELD_UPDATE_TIME,format = { DateFormat.date_optional_time, DateFormat.epoch_millis,DateFormat.epoch_second })
    private Long updateTime;

    @Field(type = FieldType.Long,name = FIELD_USER_ID)
    private long userId;

    @Field(type = FieldType.Long,name = FIELD_SUBSTANCE_ID)
    private long substanceId;

    @Field(type = FieldType.Long,name = FIELD_ROOM_ID)
    private long roomId;

    @Field(type = FieldType.Boolean,name = FIELD_DELETED)
    private boolean deleted;
}
