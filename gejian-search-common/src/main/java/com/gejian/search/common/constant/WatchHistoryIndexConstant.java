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

    /** 标题 **/
    String FIELD_TITLE = "title";

    /** 直播间id **/
    String FIELD_ROOM_ID = "room_id";

    String FIELD_USER_ID = "user_id";

    String FIELD_AUTHOR_ID = "author_id";

    String FIELD_AUTHOR_USERNAME = "author_username";

    String FIELD_AUTHOR_NICKNAME = "author_nickname";

    String FIELD_COVER_FILE_NAME = "cover_file_name";

    String FIELD_COVER_BUCKET_NAME = "cover_bucket_name";

    String FIELD_RECORD_TYPE = "record_type";

    /** * 创建时间 */
    String FIELD_CREATE_TIME = "create_time";

    /** * 是否删除 */
    String FIELD_DELETED = "deleted";

    /** * 更新时间 */
    String FIELD_UPDATE_TIME = "update_time";

}
