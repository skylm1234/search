package com.gejian.search;

import cn.hutool.core.util.StrUtil;
import com.gejian.search.common.constant.BasicConstant;
import com.gejian.search.common.constant.UserVideoIndexConstant;
import com.gejian.search.common.index.UserVideoIndex;
import com.gejian.search.web.service.SubstanceSearchService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : Hyb
 * @date : 2021/10/14 14:40
 * @description:
 */
@DisplayName("service测试")
public class UserSearchServiceTest extends BaseTest {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    public void test() {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.filter(QueryBuilders.termQuery(UserVideoIndexConstant.CREATE_USER_ID, 35));
        boolQueryBuilder.must(QueryBuilders.multiMatchQuery("背", UserVideoIndexConstant.FIELD_VIDEO_TITLE, UserVideoIndexConstant.FIELD_VIDEO_INTRODUCE).analyzer(BasicConstant.IK_MAX_WORD));
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(PageRequest.of(0, 10))
                .build();
        SearchHits<UserVideoIndex> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, UserVideoIndex.class);
        if (searchHits.getTotalHits() <= 0) {

        }
        List<Long> videoIds = searchHits.stream().map(searchHit -> searchHit.getContent().getId()).collect(Collectors.toList());
        System.out.println(videoIds);

    }
}
