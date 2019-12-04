package com.hqhop.config.dingtalk.rest;

import com.dingtalk.api.response.OapiUserGetResponse;
import com.hqhop.aop.log.Log;
import com.hqhop.config.dingtalk.DingTalkUtils;
import com.hqhop.config.dingtalk.dingtalkVo.ResultVO;
import com.hqhop.config.dingtalk.utils.ResultUtil;
import com.taobao.api.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：张丰
 * @date ：Created in 2019/11/1 0001 9:37
 * @description：钉钉接口方法
 * @modified By：
 * @version: $
 */
@Api(tags = "DingTalk接口")
@RestController
@RequestMapping("api")
public class DingTalkController {


    @Log("查询钉钉中用户信息")
    @ApiOperation(value = "查询用户信息接口ResultVo")
    @GetMapping(value = "/getUserInfo")
    public ResultVO doLogin(String requestAuthCode) throws ApiException {

        System.out.println("=============getUserInfo=============");
        String userId = DingTalkUtils.getUserId(requestAuthCode);

        System.out.println("userid:" + userId);
        OapiUserGetResponse userInfo = null;
        userInfo  = DingTalkUtils.getUserInfo(userId);

        ResultVO resultVO = ResultUtil.success(userInfo);

        // 授权当前用户具有附件的上传权限
//        DingTalkUtil.grantCustomSpace(userId, "add");
//        DingTalkUtil.grantCustomSpace(userId, "download");

        System.out.println(resultVO .getData());


        return resultVO;
    }












}
