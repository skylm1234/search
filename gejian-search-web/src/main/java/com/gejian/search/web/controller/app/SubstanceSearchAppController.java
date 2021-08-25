package com.gejian.search.web.controller.app;


import com.gejian.common.core.util.R;
import com.gejian.search.common.dto.SubstanceSearchDTO;
import com.gejian.search.web.service.SubstanceSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author:chen
 * @Date: 2021/8/4 16:13
 */
@RestController
@RequestMapping("/app/search/substance")
@Api(value = "app-search-substance", tags = "视频搜索-App")
@ApiOperation(value = "视频搜索")
public class SubstanceSearchAppController {

	@Autowired
	private SubstanceSearchService substanceSearchService;

	@GetMapping("")
	@ApiOperation("全局搜索视频，无需认证")
	public R<org.springframework.data.domain.Page> search(SubstanceSearchDTO substanceSearchDTO){
		return R.ok(substanceSearchService.search(substanceSearchDTO));
	}
}
