package com.hqhop.modules.material.domain;


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
 * 公司表
 */
@Entity
@Data
@Table(name="company")
public class Company implements Serializable {
    //公司id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long companyId;
    /*
    公司名
    */
    @Column(name = "company_name")
    private String companyName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;


    @ManyToMany(cascade = { CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "companyEntities")
    @JoinTable(name = "t_material_company", joinColumns = { @JoinColumn(name = "company_id") }, inverseJoinColumns = { @JoinColumn(name = "id") })
    private Set<Material> materials;





}
