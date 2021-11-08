package com.gejian.search.web.controller.rpc;

import com.gejian.common.core.annotation.CurrentUser;
import com.gejian.common.core.util.R;
import com.gejian.common.security.annotation.Inner;
import com.gejian.common.security.service.GeJianUser;
import com.gejian.search.common.dto.UserSearchDTO;
import com.gejian.search.web.service.SubstanceSearchService;
import com.gejian.substance.client.dto.video.UserSearchVideoViewDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * @author : Hyb
 * @date : 2021/9/27 10:40
 * @description:
 */
@ApiIgnore
@RestController
@RequestMapping("/rpc/user/search")
@Api(value = "app-user-search", tags = "用户视频搜索-App")
@ApiOperation(value ="用户视频搜索")
@Inner
public class MySearchRpcController {

    @Autowired
    private SubstanceSearchService substanceSearchService;

    @PostMapping("/video")
    @ApiOperation("搜索用户视频")
    public R<List<Long>> searchUserVideo(@RequestBody @Valid UserSearchDTO userSearchDTO) {
        return R.ok(substanceSearchService.searchUserVideoByKeyword(userSearchDTO));
    }

}
