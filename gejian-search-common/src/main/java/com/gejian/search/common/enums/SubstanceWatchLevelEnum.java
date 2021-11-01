package com.gejian.search.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author:chen
 * @Date: 2021/8/18 14:48
 */
@Getter
@AllArgsConstructor
public enum SubstanceWatchLevelEnum {

	//观看权限
	DEFAULT("default","无"),
	FREE("free", "免费"),
	LIMIT_FREE("limit_free", "限免"),
	VIP("vip", "会员"),
	CHARGE("charge", "收费");

	private final String name;

	private final String desc;


	@JsonCreator
	public SubstanceWatchLevelEnum valueOfName(String name){
		try{
			return SubstanceWatchLevelEnum.valueOf(name.toUpperCase());
		}catch (Exception e){
			return SubstanceWatchLevelEnum.DEFAULT;
		}
	}
}
