package com.hqhop.modules.security.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Getter
@AllArgsConstructor
public class JwtUser implements UserDetails {

    @JsonIgnore
    private final Long id;

    private final String username;

    private final String employeeName;


    //用户的钉钉ID
    private final String dingId;

      //用户员工主键
    private final Long employeeId;

    //用户员工工号
    private final String employeeCode;

    @JsonIgnore
    private final String password;

    private final String avatar;

    private final String email;

    private final String phone;

    private final String employee;

    private final Set<Long> deptIds;

    private final String job;

    @JsonIgnore
    private final Collection<GrantedAuthority> authorities;

    private final boolean enabled;

    private Timestamp createTime;

    private String dduserid;

    private String empnum;

    @JsonIgnore
    private final Date lastPasswordResetDate;

    //账户是否过期
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //账户是否未锁
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Collection getRoles() {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }
}
