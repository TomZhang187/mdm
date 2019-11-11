package com.hqhop.modules.material.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @author KinLin
 * @date 2019-10-30
 * 物料主表
 */
@Entity
@Getter
@Setter
@Table(name="material")
public class Material implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //标识
    @Column(name = "remark")
    private String remark;


    //大分类
    @Column(name = "big_type")
    private String bigType;

    //是否应税劳务
    private Boolean isTaxable;

    //流水码
    @Column(name = "flow_code")
    private String flowCode;

    //新物料代码
    @Column(name = "new_code",nullable = false)
    private String newCode;

    //原物料代码1
    @Column(name = "old_code_one",nullable = false)
    private String oldCode1;

    //原物料代码2
    @Column(name = "old_code_two",nullable = false)
    private String oldCode2;

    //原物料代码3
    @Column(name = "old_code_three",nullable = false)
    private String oldCode3;

    // 名称(图纸明细栏名称)
    @Column(name = "material_name",nullable = false)
    private String name;


    //规格型号(图纸明细栏规格型号)
    @Column(name = "specifications")
    private String specifications;
    //创建者
    @Column(name = "creator")
    private String createPerson;
    //创建时间
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    //修改者
    @Column(name = "modifier")
    private String modifier;

    //修改时间
    @Column(name = "modified_time")
    @CreationTimestamp
    private Timestamp modifiedTime;

    //数量
    @Column(name = "count")
    private Integer count;

    //单位
    @Column(name = "unit")
    private String unit;

    //物料类型(物料种类小类型)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"materials"})
    @JoinColumn(name = "type_id")
    private MaterialType type;

    //物料所关联的公司
    @ManyToMany(cascade = { CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "materials" })// 解决循环查找的问题
    @JoinTable(name = "t_material_company", joinColumns = { @JoinColumn(name = "id") }, inverseJoinColumns = { @JoinColumn(name = "company_id") })
    private Set<Company> companyEntities;

    public void copy(Material source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }


}