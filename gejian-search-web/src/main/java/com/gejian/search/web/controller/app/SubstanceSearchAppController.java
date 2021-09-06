package com.gejian.search.web.controller.app;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gejian.common.core.util.R;
import com.gejian.search.common.dto.SubstanceSearchDTO;
import com.gejian.search.web.service.RedisSearchService;
import com.gejian.search.web.service.SubstanceSearchService;
import com.gejian.substance.client.dto.online.app.view.OnlineSearchDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;


/**
 * @Author:lijianghuai
 * @Date: 2021/8/4 16:13
 */
@RestController
@RequestMapping("/app/search")
@Api(value = "app-search-substance", tags = "视频搜索-App")
@ApiOperation(value = "视频搜索")
public class SubstanceSearchAppController {

	@Autowired
	private SubstanceSearchService substanceSearchService;

	@Autowired
	private RedisSearchService redisSearchService;

	@GetMapping("/substance")
	@ApiOperation("全局搜索视频，无需认证")
	public R<Page<OnlineSearchDTO>> search(SubstanceSearchDTO substanceSearchDTO){
		return R.ok(substanceSearchService.search(substanceSearchDTO));
	}

	@GetMapping("/popular")
	@ApiOperation("热门搜索")
	public R<List<String>> popular(@ApiParam(required = false,defaultValue = "10",value = "返回条数，默认为10") @RequestParam(required = false,defaultValue = "10")Integer size){
		return R.ok(redisSearchService.getHotSearchList(size));
	}

	@GetMapping("/history")
	@ApiOperation("获取历史搜索")
	public R<Set<String>> getHistory(@ApiParam(defaultValue = "2",value = "返回条数，默认为2") @RequestParam(required = false,defaultValue = "2")Integer size,
								  @ApiParam(defaultValue = "1",value = "返回页数，默认为1") @RequestParam(required = false,defaultValue = "1")Integer current){
		return R.ok(redisSearchService.getHistorySearch(size,current));
	}

	@Delete("/history")
	@ApiOperation("删除历史搜索")
	public R deleteHistory(@RequestParam("content")String content){
		redisSearchService.deleteHistorySearch(content);
		return R.ok();
	}
}
