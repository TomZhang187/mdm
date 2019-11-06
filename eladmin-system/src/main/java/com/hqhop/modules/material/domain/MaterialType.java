package com.hqhop.modules.material.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @author KinLin
 * @date 2019-10-30
 * 物料分类（小类）
 */
@Entity
@Table(name="material_type")
//不加要出現Type definition error:[simple type, class org.hibernate.proxy.pojo.javassist.JavassistLazyInitializ
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class MaterialType implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     ID
      */
    @Id
    @SequenceGenerator(name="PK_SEQ_TBL",sequenceName="type_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "type_id")
    @NotNull(groups = MaterialType.Update.class)
    private Long id;

    //物料类型名称
    @Column(name = "type_name")
    @NotBlank
    private String typeName;

    /**
     * 上级分类
     */
    @Column(name = "pid",nullable = false)
    @NotNull
    private Long parentId;


    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    //属于具体的小类的物料实体
    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL,orphanRemoval = true )
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
    private Set<Material> materials;

    @ManyToMany(cascade = { CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "materialTypes" })// 解决循环查找的问题
    @JoinTable(name = "t_type_attr", joinColumns = { @JoinColumn(name = "type_id") }, inverseJoinColumns = { @JoinColumn(name = "attribute_id") })
    private Set<Attribute> attributes;

    /*public void copy(MaterialType source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }*/
    public @interface Update {}

    /**
     * 主键生成策略
     */
    @Target(value = {ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
    @Retention(value = RetentionPolicy.RUNTIME)
    public @interface SequenceGenerator {

        public String name(); //主键生成策略的名称

        public String sequenceName() default ""; //生成策略用到的数据库序列名称

        public int initialValue() default 1; //主键初识值

        public int allocationSize() default 50;  //每次主键值增加的大小
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Set<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(Set<Material> materials) {
        this.materials = materials;
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }
}
