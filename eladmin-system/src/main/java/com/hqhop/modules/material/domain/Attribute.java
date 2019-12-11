package com.hqhop.modules.material.domain;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @author KinLin
 * @date 2019-10-30
 * 物料属性类
 */
@Data
@Entity
@Table(name = "attribute")
public class Attribute implements Serializable , Comparable<Attribute>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attribute_id")
    private Long attributeId;
    /*
    属性名
    */
    @Column(name = "attr_name")
    private String attributeName;
    /*
    属性编号
     */
    @Column(name="attribute_number")
    private Integer attributeNumber;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;


    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"attributes", "materialTypes"})// 解决循环查找的问题
    @JoinTable(name = "t_type_attr", joinColumns = {@JoinColumn(name = "attribute_id")}, inverseJoinColumns = {@JoinColumn(name = "type_id")})
    private Set<MaterialType> materialTypes;
    //物料属性
    /*@ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"materials"})// 解决循环查找的问题
    @JoinTable(name = "t_material_attribute", joinColumns = {@JoinColumn(name = "attribute_id")}, inverseJoinColumns = {@JoinColumn(name = "material_id")})*/
    @Transient
    private Set<Material> materials;
    @Transient
    private String attributeValue;

    public void copy(Attribute source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }

    /**
     * 对属性进行排序
     * @param o
     * @return
     */
    @Override
    public int compareTo(Attribute o) {
        return (this.getAttributeNumber()-o.getAttributeNumber());
    }
}
