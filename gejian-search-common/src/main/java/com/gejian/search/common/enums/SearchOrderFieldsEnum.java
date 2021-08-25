package com.gejian.search.common.enums;

import java.util.Arrays;
import java.util.Optional;

import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_BULLET_COUNT;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_COLLECT_COUNT;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_COMMENT_COUNT;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_CREATE_TIME;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_FORWARD_COUNT;
import static com.gejian.search.common.constant.SubstanceOnlineIndexConstant.FIELD_PLAY_COUNT;

/**
 * @author ：lijianghuai
 * @date ：2021-08-24 9:51
 * @description：查询排序枚举
 */
public enum SearchOrderFieldsEnum {
	/**
	 * 默认排序
	 */
	DEFAULT,
	/**
	 * 最新
	 */
	NEWEST,
	/**
	 * 播放最多
	 */
	PLAY_MOST,
	/**
	 * 评论最多
	 */
	COMMENT_MOST,
	/**
	 * 弹幕最多
	 */
	BULLET_MOST,
	/**
	 * 收藏最多
	 */
	COLLECT_MOST,
	/**
     * 转发最多
	 */
	FORWARD_MOST,
	NEWEST_ORDER(NEWEST.name(),FIELD_CREATE_TIME),
	PLAY_MOST_ORDER(PLAY_MOST.name(),FIELD_PLAY_COUNT),
	COMMENT_MOST_ORDER(COMMENT_MOST.name(),FIELD_COMMENT_COUNT),
	BULLET_MOST_ORDER(BULLET_MOST.name(),FIELD_BULLET_COUNT),
	COLLECT_MOST_ORDER(COLLECT_MOST.name(),FIELD_COLLECT_COUNT),
	FORWARD_MOST_ORDER(FORWARD_MOST.name(),FIELD_FORWARD_COUNT),
	;

	SearchOrderFieldsEnum(){}
	SearchOrderFieldsEnum(String val,String filedMapping){
		this.val = val;
		this.filedMapping = filedMapping;
	}
	private String val;
	private String filedMapping;

	/**
	 * 获取映射的排序字段
	 * @return
	 */
	public Optional<String> fieldMapping(){
		Optional<SearchOrderFieldsEnum> fieldsEnum = Arrays.stream(SearchOrderFieldsEnum.values()).filter(e -> this.name().equals(e.val)).findFirst();
		if(fieldsEnum.isPresent()){
			return Optional.ofNullable(fieldsEnum.get().filedMapping);
		}else{
			return Optional.empty();
		}
	}
}
