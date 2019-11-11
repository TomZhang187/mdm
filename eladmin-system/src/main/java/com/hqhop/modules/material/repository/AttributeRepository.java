package com.hqhop.modules.material.repository;

import com.hqhop.modules.material.domain.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author KinLin
* @date 2019-10-30
 * 物料属性dao
*/
public interface AttributeRepository extends JpaRepository<Attribute, Long>, JpaSpecificationExecutor {




    /**
     * findByMaterialTypeAndId
     * @param id
     * @return
     */
    @Query(value = "select a.attribute_id,a.attr_name,a.attr_value,a.create_time from attribute a inner join t_type_attr m on a.attribute_id=m.attribute_id where m.type_id=?1",nativeQuery = true)
    List<Attribute> findByMaterialTypeId(Long id);

    /**
     * 查看是否以存在
     * @param name
     * @param value
     * @return
     */
    Attribute findAttributesByAttributeNameAndAttributeValue(String name, String value);
}