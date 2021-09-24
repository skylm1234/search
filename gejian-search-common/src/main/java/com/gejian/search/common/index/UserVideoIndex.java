package com.gejian.search.common.index;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import static com.gejian.search.common.constant.UserVideoIndexConstant.INDEX_NAME;


/**
 * @author : Hyb
 * @date : 2021/9/24 10:03
 * @description:
 */
@Data
@Accessors(chain = true)
@Document(indexName = INDEX_NAME)
public class UserVideoIndex {

    @Id
    private Long id;
}
