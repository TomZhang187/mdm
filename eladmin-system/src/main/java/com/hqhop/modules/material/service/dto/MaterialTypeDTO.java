package com.hqhop.modules.material.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
public class MaterialTypeDTO implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 名称
     */
    private String typeName;

    /**
     * 上级分类
     */
    private Long parentId;

    /**
    子级分类
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<MaterialTypeDTO> children;


    /**
     * 创建时间
     */
    private Timestamp createTime;

    public String getLabel() {
        return typeName;
    }
}