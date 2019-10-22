package me.zhengjie.modules.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hqhop.modules.employee.domain.Employee;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.io.Serializable;
import java.util.Set;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@Entity
@Data
@Table(name="sys_dept")
public class Dept implements Serializable {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = Update.class)
    private Long id;

    /**
     * 名称
     */
    @Column(name = "name",nullable = false)
    @NotBlank
    private String name;

    @NotNull
    private Boolean enabled;

    /**
     * 上级部门
     */
    @Column(name = "pid",nullable = false)
    @NotNull
    private Long pid;

    @JsonIgnore
    @ManyToMany(mappedBy = "depts")
    private Set<Role> roles;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    @Column(name = "employee_code")
    private String employeeCode;

    @ManyToOne
    @JoinColumn(name = "leader_id")
    private Employee leader;

    public @interface Update {}
}
