package com.hqhop.modules.material.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


/**
* @author KinLin
* @date 2019-10-30
*/
@Data
public class MaterialDTO implements Serializable {

    // ID
    private Long id;

    // 创建时间
    private Timestamp createTime;


    // 名称
    private String name;


}