package com.hqhop.modules.material.service.dto;

import lombok.Data;
import java.io.Serializable;


/**
* @author chengy
* @date 2019-10-17
*/
@Data
public class MaterialDTO implements Serializable {

    private Integer id;

    private String materialModel;

    private String materialName;

    private String materialNumber;
}