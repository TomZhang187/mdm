package com.hqhop.modules.system.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hqhop.modules.system.domain.Employee;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Data
public class UserDTO implements Serializable {

    @ApiModelProperty(hidden = true)
    private Long id;

    private String username;

    private String avatar;

    private String email;

    private String phone;

    private Boolean enabled;

    @JsonIgnore
    private String password;

    private Timestamp createTime;

    private Set<Long> depts;

    private String dduserid;

    private String empnum;

    private Date lastPasswordResetTime;


    @ApiModelProperty(hidden = true)
    private Set<RoleSmallDTO> roles;

    @ApiModelProperty(hidden = true)
    private JobSmallDTO job;

    @ApiModelProperty(hidden = true)
    private EmployeeSmallDTO employee;



    public Long getDeptId(){
        Long id =-1L;
        if(depts !=null){

            for (Long dept : depts) {
                  id = dept;
                  break;
            }
        }

        return   id;
    }



}
