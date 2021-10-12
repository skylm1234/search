package com.gejian.search.common.dto;

import com.gejian.common.core.util.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanxue
 * @date 2021-10-11
 */
@Data
@ApiModel("热搜新增/编辑dto")
public class HotSearchQueryDTO extends BasePageQuery {

    @ApiModelProperty("话题内容")
    private String content;

}
