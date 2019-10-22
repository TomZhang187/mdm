package com.hqhop.modules.employee.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.Role;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="employee")
public class Employee implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Employee.Update.class)
    @Column(name = "id")
    private Long id;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "employee_phone",unique = true)
    private String employeePhone;

    @Column(name = "employee_card_id",unique = true)
    @NotBlank
    private String employeeCardId;

    @Column(name = "employee_sex")
    private String employeeSex;

    @Column(name = "employee_code",unique = true)
    @NotBlank
    private String employeeCode;

    @Column(name = "employee_state")
    @NotNull
    private int employeeState;

    @ManyToMany
    @JoinTable(name = "employees_depts", joinColumns = {@JoinColumn(name = "employee_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "dept_id", referencedColumnName = "id")})
    private Set<Dept> depts;//所属部门

    @ManyToMany
    @JoinTable(name = "employees_roles", joinColumns = {@JoinColumn(name = "employee_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles;//所有角色

    public void copy(Employee source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }

    public @interface Update {}
}
