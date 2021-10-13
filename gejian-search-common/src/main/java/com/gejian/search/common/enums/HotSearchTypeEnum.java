package com.gejian.search.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author ：yuanxue
 * @date ：2021-10-12
 * @description：
 */
@Getter
@AllArgsConstructor
public enum HotSearchTypeEnum {
	/**
	 * 0-无，1-热，2-新，3-荐，4-商
	 */
	NONE_TOPIC(0, "无"),
	HOT_TOPIC(1, "热"),
	NEW_TOPIC(2, "新"),
	RECOMMEND_TOPIC(3, "荐"),
	BUSINESS_TOPIC(4, "商");

	 private Integer code;
	 private String type;

	public static HotSearchTypeEnum getType(Integer code) {
		return Arrays.stream(HotSearchTypeEnum.values())
				.filter(hotSearchTypeEnum -> hotSearchTypeEnum.getCode().equals(code))
				.findFirst().orElse(HotSearchTypeEnum.NONE_TOPIC);
	}
}
