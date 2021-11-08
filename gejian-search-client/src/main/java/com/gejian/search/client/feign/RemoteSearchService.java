package com.gejian.search.client.feign;

import com.gejian.common.core.constant.SecurityConstants;
import com.gejian.common.core.util.R;
import com.gejian.search.client.feign.factory.RemoteSearchServiceFallbackFactory;
import com.gejian.search.common.dto.UserSearchDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * @author Administrator
 */
@FeignClient(contextId = "remoteSearchService", value = "gejian-search-web",
		fallbackFactory = RemoteSearchServiceFallbackFactory.class)
public interface RemoteSearchService {


	/**
	 * 根据关键字搜索我的视频列表
	 * @param userSearchDTO
	 * @return
	 */
	@PostMapping("/rpc/user/search/video")
	R<List<Long>> searchMyVideoByKeyword(@RequestBody UserSearchDTO userSearchDTO
			, @RequestHeader(SecurityConstants.FROM)String from);

}
