package com.hqhop.modules.security.config;

import com.hqhop.modules.security.security.JwtAuthenticationEntryPoint;
import com.hqhop.modules.security.security.JwtAuthorizationTokenFilter;
import com.hqhop.modules.security.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/*
* @EnableGlobalMethodSecurity 开启注解的权限控制,默认是关闭的
*
*
*
* */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    /**
     * 自定义基于JWT的安全过滤器
     */
    @Autowired
    JwtAuthorizationTokenFilter authenticationTokenFilter;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.auth.path}")
    private String loginPath;


    /*
    从容器取出AuthenticationManagetBuilder,执行方法里的逻辑之后，放回容器
    * */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(jwtUserDetailsService)
                .passwordEncoder(passwordEncoderBean());
    }

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        // Remove the ROLE_ prefix
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity

                // 禁用 CSRF
                .csrf().disable()

                // 授权异常
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()

                // 不创建会话
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                // 过滤请求
                .authorizeRequests()
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).anonymous()

                .antMatchers( HttpMethod.POST,"/auth/"+loginPath).anonymous()
                .antMatchers("/auth/vCode").anonymous()
                // 支付宝回调
                .antMatchers("/api/aliPay/return").anonymous()
                .antMatchers("/api/aliPay/notify").anonymous()
                //钉钉回调
                .antMatchers("/ding/companyCallback").anonymous()
                .antMatchers("/ding/materialCallback").anonymous()
                .antMatchers("/ding/materialProductionCallback").anonymous()
                // swagger start
                .antMatchers("/swagger-ui.html").anonymous()
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/*/api-docs").anonymous()
                // swagger end

                // 接口限流测试
                .antMatchers("/test/**").anonymous()
                // 文件
                .antMatchers("/avatar/**").anonymous()
                .antMatchers("/file/**").anonymous()

                // 放行OPTIONS请求
                .antMatchers(HttpMethod.OPTIONS, "/**").anonymous()

                .antMatchers("/druid/**").anonymous()
                // 所有请求都需要认证
//                .anyRequest().authenticated()
                // 防止iframe 造成跨域
                .and().headers().frameOptions().disable();

        httpSecurity
                .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
