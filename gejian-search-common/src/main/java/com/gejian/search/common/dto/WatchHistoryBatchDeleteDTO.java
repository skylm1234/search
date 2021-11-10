package com.gejian.search.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ：lijianghuai
 * @date ：2021-11-10 9:43
 * @description：
 */

@ApiModel("批量删除观看记录DTO")
@Data
public class WatchHistoryBatchDeleteDTO {

    @ApiModelProperty(value = "观看记录集合",required = true)
    private List<WatchHistoryDeleteDTO> historys;
}
