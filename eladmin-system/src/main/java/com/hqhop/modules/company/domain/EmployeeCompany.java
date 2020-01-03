package com.hqhop.modules.company.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import java.io.Serializable;

/**
* @author zf
* @date 2020-01-02
*/
@Entity
@Data
@Table(name="employee_company")
public class EmployeeCompany implements Serializable {


    // 主键
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;



    @Column(name = "employee_key")
    private Long employeeKey;


    @Column(name = "company_key")
    private Long companyKey;

    public void copy(EmployeeCompany source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}