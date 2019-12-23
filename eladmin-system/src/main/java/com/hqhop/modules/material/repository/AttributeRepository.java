package com.hqhop.modules.material.repository;

import com.hqhop.modules.material.domain.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author KinLin
 * @date 2019-10-30
 * 物料属性dao
 */
public interface AttributeRepository extends JpaRepository<Attribute, Long>, JpaSpecificationExecutor {


    /**
     * findByMaterialTypeAndId
     *
     * @param id
     * @return
     */
    @Query(value = "select a.attribute_id,a.attr_name,a.create_time,a.attribute_number from attribute a inner join t_type_attr m on a.attribute_id=m.attribute_id where m.type_id=?1 order by a.attribute_number", nativeQuery = true)
    List<Attribute> findByMaterialTypeId(Long id);

    /**
     * 查看是否已存在
     *
     * @param name
     * @return
     */
    Attribute findAttributesByAttributeName(String name);

    /**
     * 自定义删除中间表需要打的注解
     * @param id
     * @param attributeId
     */
    @Transactional
    @Modifying
    @Query(value = "delete from  t_type_attr  where type_id=?1 and attribute_id=?2", nativeQuery = true)
    void attributeDelete(Long id, Long attributeId);

    /**
     * 自定义修改属性名称
     * @param attributeName
     * @param attributeId
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE attribute set attr_name=?1 where attribute_id=?2",nativeQuery = true)
    void updateAttribute(String attributeName,Long attributeId);

    @Query(value = "select a.attribute_id,a.attr_name,a.create_time,m.attribute_value,attribute_number from attribute a inner join material_attribute m on m.attribute_id=a.attribute_id where m.material_id=?1 order by a.attribute_number", nativeQuery = true)
    List<Attribute> queryAllByMaterialId(Long id);
    Attribute findAttributeByAttributeName(String attributeName);
    @Query(value = "select * from attribute a inner join t_type_attr m on m.attribute_id=a.attribute_id where m.attribute_id=?1 and m.type_id=?2", nativeQuery = true)
    Attribute findAttributeByAttributeIdAndMaterialsTypeId(Long attributeId,Long ypeId);

    /**
     * 为type设置已有属性
     * @param attributeId
     * @param typeId
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO t_type_attr (attribute_id,type_id) values (?1,?2)", nativeQuery = true)
    void setTypeAttribute(Long attributeId, Long typeId);



    @Query(value = "select * from attribute  where attr_name=?1 ", nativeQuery = true)
    Attribute findByAttributeName(String name);



    @Query(value = "select count(*) from t_type_attr  where attribute_id=?1 and type_id=?2  ", nativeQuery = true)
   Integer findT_Type_att(Long attributeId,Long typId);
}