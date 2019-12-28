package com.hqhop.modules.material.service.dto;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hqhop.modules.material.domain.Material;
import com.hqhop.modules.material.domain.MaterialType;
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
public class AttributeDto implements Serializable {

    private Long attributeId;
    /*
    属性名
    */
    private String attributeName;

    private Integer attributeNumber;

    private String attributeValue;


}
