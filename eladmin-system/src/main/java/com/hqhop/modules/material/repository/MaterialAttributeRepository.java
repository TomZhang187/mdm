package com.hqhop.modules.material.repository;

import com.hqhop.modules.material.domain.MaterialAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author KinLin
 * @date 2019-10-30
 * 物料属性dao
 */
public interface MaterialAttributeRepository extends JpaRepository<MaterialAttribute, Long>, JpaSpecificationExecutor {
    void deleteByMaterialId(Long id);
    void deleteByAttributeAttributeId(Long id);
    /**
     * 查询指定函数的值,用于赋值
     *
     * @param attributeId
     * @param materialId
     * @return
     */
    @Query(value = "select * from material_attribute where attribute_id=?1 and material_id=?2", nativeQuery = true)
    MaterialAttribute findByAttributeId(Long attributeId, Long materialId);

    /**
     * 设置属性值
     * @param attributeValue
     * @param attributeId
     * @param materialId
     */
    @Transactional
    @Modifying
    @Query(value = "update material_attribute set attribute_value=?1  where attribute_id=?2 and material_id=?3", nativeQuery = true)
    void updateByAttributeId(String attributeValue, Long attributeId, Long materialId);

}