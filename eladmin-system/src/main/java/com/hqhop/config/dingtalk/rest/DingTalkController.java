package com.hqhop.config.dingtalk.rest;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCspaceGrantCustomSpaceRequest;
import com.dingtalk.api.request.OapiProcessinstanceCspaceInfoRequest;
import com.dingtalk.api.response.OapiCspaceGetCustomSpaceResponse;
import com.dingtalk.api.response.OapiCspaceGrantCustomSpaceResponse;
import com.dingtalk.api.response.OapiProcessinstanceCspaceInfoResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.hqhop.aop.log.Log;
import com.hqhop.config.dingtalk.DingTalkConstant;
import com.hqhop.config.dingtalk.DingTalkUtils;
import com.hqhop.config.dingtalk.dingtalkVo.ResultVO;
import com.hqhop.config.dingtalk.utils.ResultUtil;
import com.hqhop.modules.system.service.UserService;
import com.hqhop.modules.system.service.dto.UserDTO;
import com.hqhop.utils.SecurityUtils;
import com.taobao.api.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.ServletContextPropertyUtils;

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

    @Autowired
    private UserService userService;

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

    @Log("获取空间ID")
    @ApiOperation(value = "获取审批钉盘空间ID")
    @GetMapping(value = "/getApprovalSpaceId")
    public ResponseEntity getSpaceId() {

        UserDTO userDTO = userService.findByName(SecurityUtils.getUsername());
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/cspace/info");
        OapiProcessinstanceCspaceInfoRequest req = new OapiProcessinstanceCspaceInfoRequest();
        req.setUserId(userDTO.getEmployee().getDingId());
        OapiProcessinstanceCspaceInfoResponse rsp = null;
        try {
            rsp = client.execute(req, DingTalkUtils.getAccessToken());
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
        return new ResponseEntity(rsp.getResult().getSpaceId(),HttpStatus.OK);
    }



    @Log("授权用户 上传和下载自定义空间权限 默认为/material路径")
    @ApiOperation(value = "授权用户空间权限")
    @GetMapping(value = "/getSpacePermission")
    public ResponseEntity grantCustomSpace(String type,String fileids) throws ApiException {


        UserDTO userDTO = userService.findByName(SecurityUtils.getUsername());
        OapiProcessinstanceCspaceInfoResponse processSpace = DingTalkUtils.getProcessSpace(userDTO.getEmployee().getDingId());
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/cspace/grant_custom_space");
        OapiCspaceGrantCustomSpaceRequest request = new OapiCspaceGrantCustomSpaceRequest();
        request.setAgentId(DingTalkConstant.AGENTID);
        request.setDomain("material"); // 企业内部调用时传入，授权访问该domain的自定义空间
        request.setType(type); // 权限类型，目前支持上传和下载，上传请传add，下载请传download
        request.setUserid(userDTO.getEmployee().getDingId()); // 企业用户userid
        request.setPath("/"); // 授权访问的路径，如授权访问所有文件传"/"，授权访问/doc文件夹传"/doc/"，需要utf-8 urlEncode, type=add时必须传递
        request.setDuration(3600L); // 权限有效时间，有效范围为0~3600秒
        request.setHttpMethod("GET");
        if ("download".equals(type)) {
            request.setFileids(fileids);
        }
        OapiCspaceGrantCustomSpaceResponse response = client.execute(request, DingTalkUtils.getAccessToken());
       return new ResponseEntity(response.getErrmsg(),HttpStatus.OK);
    }






}
