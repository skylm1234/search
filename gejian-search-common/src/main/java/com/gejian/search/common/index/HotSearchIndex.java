package com.gejian.search.common.index;

import com.gejian.search.common.constant.BasicConstant;
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

import static com.gejian.search.common.constant.HotSearchIndexConstant.*;

/**
 * @author ：yuanxue
 * @date ：2021-10-11
 * @description：
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document(indexName = INDEX_NAME)
@Builder
public class HotSearchIndex {

    @Id
    private String id;

    @Field(type = FieldType.Integer,name = FIELD_RANKING)
    private Integer ranking;

    @Field(type = FieldType.Text,name = FIELD_CONTENT,analyzer = BasicConstant.IK_SMART)
    private String content;

    @Field(type = FieldType.Text,name = FIELD_ASSOCIATED_WORD)
    private String associatedWord;

    @Field(type = FieldType.Text,name = FIELD_MARKING)
    private String marking;

    @Field(type = FieldType.Boolean,name = FIELD_STICK)
    private Boolean stick;

    @Field(type = FieldType.Date,name = FIELD_CREATE_TIME,format = { DateFormat.date_time_no_millis, DateFormat.epoch_millis,DateFormat.epoch_second })
    private Long createTime;

    @Field(type = FieldType.Date,name = FIELD_UPDATE_TIME,format = { DateFormat.date_time_no_millis, DateFormat.epoch_millis,DateFormat.epoch_second })
    private Long updateTime;

    @Field(type = FieldType.Boolean,name = FIELD_DELETED)
    private Boolean deleted;
}
