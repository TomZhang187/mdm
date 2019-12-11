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
import org.springframework.web.bind.annotation.*;
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

    @Log("获取审批钉盘空间ID")
    @ApiOperation(value = "获取审批钉盘空间ID")
    @GetMapping(value = "/getApprovalSpaceId")
    public ResponseEntity getSpaceId() throws ApiException{

        UserDTO userDTO = userService.findByName(SecurityUtils.getUsername());
        OapiProcessinstanceCspaceInfoResponse rsp = DingTalkUtils.getProcessSpace(userDTO.getEmployee().getDingId());
        return new ResponseEntity(rsp.getResult().getSpaceId(),HttpStatus.OK);
    }



    @Log("授权用户 上传和下载自定义空间权限 默认为/mdm路径")
    @ApiOperation(value = "授权用户空间权限")
    @GetMapping(value = "/getSpacePermission")
    public ResponseEntity grantCustomSpace(@RequestParam( value="type")String type,@RequestParam( value="fileids") String fileids) throws ApiException {
          String type2 = null;
        UserDTO userDTO = userService.findByName(SecurityUtils.getUsername());
        if("add".equals(type)){
            type2 = "add";
        }else {
            type2 = "download ";
        }
        OapiCspaceGrantCustomSpaceResponse response = DingTalkUtils.grantCustomSpace(userDTO.getEmployee().getDingId(),type2,fileids);
       return new ResponseEntity(response.getErrmsg(),HttpStatus.OK);
    }



    @Log("获取企业自定义空间ID")
    @ApiOperation(value = "获取企业自定义空间ID")
    @GetMapping(value = "/getCustomSpaceId")
    public ResponseEntity getCustomSpace() throws ApiException{

        OapiCspaceGetCustomSpaceResponse response = DingTalkUtils.getCustomSpace();
        return new ResponseEntity(response.getSpaceid(),HttpStatus.OK);
    }


}
