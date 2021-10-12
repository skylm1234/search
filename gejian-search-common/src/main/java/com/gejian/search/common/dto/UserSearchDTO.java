package com.gejian.search.common.dto;

import com.gejian.common.core.util.BasePageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author : Hyb
 * @date : 2021/9/24 11:22
 * @description:
 */
@Data
public class UserSearchDTO extends BasePageQuery {

    /**
     * 搜索关键词
     */
    @ApiModelProperty(value = "搜索关键词", required = false)
    private String keywork;

    /**
     * 查看的用户IDS
     */
    @ApiModelProperty(value = "查看的用户ID", required = false)
    private String lookUserId;

}
