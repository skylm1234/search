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

import java.time.LocalDateTime;

import static com.gejian.search.common.constant.SearchHistoryIndexConstant.FIELD_CONTENT;
import static com.gejian.search.common.constant.SearchHistoryIndexConstant.FIELD_CREATE_TIME;
import static com.gejian.search.common.constant.SearchHistoryIndexConstant.INDEX_NAME;

/**
 * @author lijianghuai
 * 搜索树索引
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document(indexName = INDEX_NAME)
@Builder
public class SearchHistoryIndex {
	@Id
	private Long id;

	@Field(type = FieldType.Text,name = FIELD_CONTENT,analyzer  = BasicConstant.IK_SMART,searchAnalyzer = BasicConstant.IK_SMART,fielddata = true)
	private String content;

	@Field(type = FieldType.Date,name = FIELD_CREATE_TIME,format = { DateFormat.date_optional_time, DateFormat.epoch_millis,DateFormat.epoch_second })
	private LocalDateTime createTime;

}
