package com.gejian.search.common.constant;

/**
 * @author ：lijianghuai
 * @date ：2021-09-06 15:23
 * @description：
 */
public interface RedisConstant {

    String KEY_PREFIX = "gejian-search:";

    String SEARCH_LATEST_AT = "search-latest-at";

    String SEARCH_SCORE = "search-score";

    String SEARCH_HISTORY = "search-history:";

    Long MONTH_MILLIS = 2592000000L;
}
