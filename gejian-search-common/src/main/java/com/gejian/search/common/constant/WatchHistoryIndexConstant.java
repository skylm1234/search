package com.gejian.search.common.constant;

/**
 * @author ：lijianghuai
 * @date ：2021-09-07 10:56
 * @description：观看历史
 */
public interface WatchHistoryIndexConstant {
    /** * 索引名字 */
    String INDEX_NAME = "watch_history";
    /** * id */
    String FIELD_ID = "id";

    /** * room,substance */
    String FIELD_TYPE = "type";

    /** 视频id **/
    String FIELD_SUBSTANCE_ID = "substance_id";

    /** 直播间id **/
    String FIELD_ROOM_ID = "room_id";

    String FIELD_USER_ID = "user_id";

    /** * 创建时间 */
    String FIELD_CREATE_TIME = "create_time";

    /** * 是否删除 */
    String FIELD_DELETED = "deleted";

    /** * 更新时间 */
    String FIELD_UPDATE_TIME = "update_time";

}