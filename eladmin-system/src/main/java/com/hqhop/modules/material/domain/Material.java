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
import java.util.List;
import java.util.Set;

/**
 * @author KinLin
 * @date 2019-10-30
 * 物料主表
 */
@Entity
@Getter
@Setter
@Table(name = "material")
public class Material implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //标识存货编码
    @Column(name = "remark")
    private String remark;

    //存货名称
    @Column(name = "name")
    private String name;

    //是否应税劳务
    @Column(name = "is_taxable")
    private Boolean isTaxable;

    //规格型号
    @Column(name = "model")
    private String model;

    //税目
    @Column(name = "tax_rating")
    private String taxRating;

    //创建者
    @Column(name = "creator")
    private String createPerson;
    //创建时间
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    //修改者
    @Column(name = "update_person")
    private String updatePerson;

    //修改日期
    @Column(name = "update_time")
    @CreationTimestamp
    private Timestamp updateTime;

    //单位
    @Column(name = "unit")
    private String unit;

    //审批状态
    @Column(name = "approval_state")
    private String approvalState;



    //物料类型(物料种类小类型)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"materials"})
    @JoinColumn(name = "type_id")
    private MaterialType type;

    //物料所关联的公司
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"materials"})// 解决循环查找的问题
    @JoinTable(name = "t_material_company", joinColumns = {@JoinColumn(name = "id")}, inverseJoinColumns = {@JoinColumn(name = "company_id")})
    private Set<Company> companyEntities;
    //物料属性
    /*@ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"attributes"})// 解决循环查找的问题
    @JoinTable(name = "t_material_attribute", joinColumns = {@JoinColumn(name = "material_id")}, inverseJoinColumns = {@JoinColumn(name = "attribute_id")})*/
    @Transient
    private Set<Attribute> attributes;
    @Transient
    private List<MaterialAttribute> materialAttributes;


    public void copy(Material source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }


}