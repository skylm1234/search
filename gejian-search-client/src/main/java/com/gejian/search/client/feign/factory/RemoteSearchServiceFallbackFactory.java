package com.gejian.search.client.feign.factory;

import com.gejian.search.client.feign.RemoteSearchService;
import com.gejian.search.client.feign.fallback.RemoteSearchServiceFallbackImpl;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * @author zhouqiang
 * @date 2021/8/9
 */
public class RemoteSearchServiceFallbackFactory implements FallbackFactory<RemoteSearchService> {

	@Override
	public RemoteSearchService create(Throwable throwable) {
		RemoteSearchServiceFallbackImpl remoteSearchServiceFallback = new RemoteSearchServiceFallbackImpl();
		remoteSearchServiceFallback.setCause(throwable);
		return remoteSearchServiceFallback;
	}

}
