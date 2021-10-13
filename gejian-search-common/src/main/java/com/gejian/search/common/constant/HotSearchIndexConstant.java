package com.gejian.search.common.constant;

/**
 * @author ：yuanxue
 * @date ：2021-10-11
 * @description：
 */
public interface HotSearchIndexConstant {
    /** * 索引名字 */
    String INDEX_NAME = "hot_search";
    /** * id */
    String FIELD_ID = "id";
    /** * 排名 */
    String FIELD_RANKING = "ranking";
    /** * 话题内容 */
    String FIELD_CONTENT = "content";
    /** * 关联词段 */
    String FIELD_ASSOCIATED_WORD = "associatedWord";
    /** * 话题标识 */
    String FIELD_MARKING = "marking";
    /** * 是否置顶 */
    String FIELD_STICK = "stick";
    /** * 创建时间 */
    String FIELD_CREATE_TIME = "create_time";
    /** * 是否删除 */
    String FIELD_DELETED = "deleted";
    /** * 更新时间 */
    String FIELD_UPDATE_TIME = "update_time";
    /** * 最大排名值50 */
    Integer MAX_RANKING = 51;

}
