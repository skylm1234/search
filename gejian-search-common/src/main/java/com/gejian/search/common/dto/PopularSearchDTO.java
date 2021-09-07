package com.gejian.search.common.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author ZhouQiang
 * @date 2021/9/7$
 */
@Data
public class PopularSearchDTO implements Serializable {

    @NotNull(message = "条数不能为空！")
    private Integer size;
}
