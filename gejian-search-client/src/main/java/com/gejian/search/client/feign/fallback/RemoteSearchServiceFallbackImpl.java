package com.gejian.search.client.feign.fallback;

import cn.hutool.core.collection.ListUtil;
import com.gejian.common.core.util.R;
import com.gejian.search.client.feign.RemoteSearchService;
import com.gejian.search.common.dto.UserSearchDTO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhouqiang
 * @date 2021/8/9
 */
@Slf4j
@Component
public class RemoteSearchServiceFallbackImpl implements RemoteSearchService {

	@Setter
	private Throwable cause;

	@Override
	public R<List<Long>> searchMyVideoByKeyword(UserSearchDTO userSearchDTO, String from) {
		log.error("根据关键字搜索我的视频列表出现错误！param:{}",userSearchDTO,cause);
		return R.ok(ListUtil.empty());
	}
}
