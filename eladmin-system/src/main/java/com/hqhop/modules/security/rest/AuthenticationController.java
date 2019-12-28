package com.hqhop.modules.security.rest;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.hqhop.common.dingtalk.DingTalkConstant;
import com.hqhop.common.dingtalk.DingTalkUtils;
import com.hqhop.exception.BadRequestException;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import com.hqhop.aop.log.Log;
import com.hqhop.modules.monitor.service.RedisService;
import com.hqhop.modules.security.security.AuthenticationInfo;
import com.hqhop.modules.security.security.AuthorizationUser;
import com.hqhop.modules.security.security.ImgResult;
import com.hqhop.modules.security.security.JwtUser;
import com.hqhop.modules.security.utils.JwtTokenUtil;
import com.hqhop.modules.security.utils.VerifyCodeUtils;
import com.hqhop.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 * 授权、根据token获取用户详细信息
 */
@Slf4j
@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisService redisService;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    /**
     * 登录授权
     *
     * @param authorizationUser
     * @return
     */
    @Log("用户登录")
    @PostMapping(value = "${jwt.auth.path}")
    public ResponseEntity login(@Validated @RequestBody AuthorizationUser authorizationUser) {

        // 查询验证码
//        String code = redisService.getCodeVal(authorizationUser.getUuid());
        // 清除验证码
//        redisService.delete(authorizationUser.getUuid());
//        if (StringUtils.isBlank(code)) {
//            throw new BadRequestException("验证码已过期");
//        }
//        if (StringUtils.isBlank(authorizationUser.getCode()) || !authorizationUser.getCode().equalsIgnoreCase(code)) {
//            throw new BadRequestException("验证码错误");
//        }
       String username = null;
        // 系统上线后 只保留钉钉登录的功能
        if (!authorizationUser.getUsername().equals(DingTalkConstant.CORPID)) { // 账号为Corpid的数据为钉钉传过来的数据
//            throw new BadRequestException("非法请求，只能在钉钉中打开应用");
            username = authorizationUser.getUsername();
        } else {
            log.info("请求的requestCode为:{}", authorizationUser.getPassword());
            String userId = null;
            OapiUserGetResponse userInfo = null;
            try {
                userId = DingTalkUtils.getUserId(authorizationUser.getPassword());
                userInfo = DingTalkUtils.getUserInfo(userId);
            } catch (ApiException e) {
                e.printStackTrace();
                throw new BadRequestException("钉钉接口异常："+ e.getErrMsg());
            }
            username = userInfo.getJobnumber();
        }

        final JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(username);

        if (!jwtUser.isEnabled()) {
            throw new AccountExpiredException("账号已停用，请联系管理员");
        }

        // 生成令牌
        final String token = jwtTokenUtil.generateToken(jwtUser);

        // 返回 token
        return ResponseEntity.ok(new AuthenticationInfo(token, jwtUser));
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping(value = "${jwt.auth.account}")
    public ResponseEntity getUserInfo() {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        return ResponseEntity.ok(jwtUser);
    }

    /**
     * 获取验证码
     */
    @GetMapping(value = "vCode")
    public ImgResult getCode(HttpServletResponse response) throws IOException {

        //生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        String uuid = IdUtil.simpleUUID();
        redisService.saveCode(uuid, verifyCode);
        // 生成图片
        int w = 111, h = 36;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        VerifyCodeUtils.outputImage(w, h, stream, verifyCode);
        try {
            return new ImgResult(Base64.encode(stream.toByteArray()), uuid);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            stream.close();
        }
    }

    /**
     * 鉴权
     */
    @GetMapping(value = "dingAuth")
    public ResponseEntity getDingAuth(String url) throws IOException {
        HttpServletRequest request = null;
        try {
            return ResponseEntity.ok(DingTalkUtils.getConfig(url));
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }
}
