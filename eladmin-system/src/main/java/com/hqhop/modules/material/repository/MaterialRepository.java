package com.hqhop.modules.material.repository;

import com.hqhop.modules.material.domain.Attribute;
import com.hqhop.modules.material.domain.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author KinLin
 * @date 2019-10-30
 */
public interface MaterialRepository extends JpaRepository<Material, Long>, JpaSpecificationExecutor {

    /**
     * 查找小类的所有物料
     *
     * @param typeId
     * @return
     */
    @Query(value = "select * from material m inner join material_type mt on m.type_id=mt.type_id" +
            " where m.type_id=?1" +
            " ORDER BY m.create_time DESC " +
            " limit ?3 offset ?2", nativeQuery = true)
    List<Material> findAllByType(Long typeId, Integer pageNo, Integer pageSize);

    @Query(value = "select count(id) from material m inner join material_type mt on m.type_id=mt.type_id " +
            " where m.type_id=?1", nativeQuery = true)
    Integer getCountByTypeId(Long typeId);

    @Query(value = "select * from material m inner join material_type mt on m.type_id=mt.type_id" +
            " where m.type_id " +
            " in (select mt.type_id from material_type mt where mt.pid=?1)" +
            "and m.enable='true'"+
            " ORDER BY m.create_time DESC " +
            " limit ?3 offset ?2", nativeQuery = true)
    List<Material> findAllBySecondaryType(Long typeId, Integer pageNo, Integer pageSize);

    @Query(value = "select  count(id) from material m inner join material_type mt on m.type_id=mt.type_id" +
            " where m.type_id " +
            " in (select mt.type_id from material_type mt where mt.pid=?1)", nativeQuery = true)
    Integer getCountBySecondaryType(Long typeId);

    @Query(value = "select * from material m inner join material_type mt on m.type_id=mt.type_id" +
            " where mt.pid " +
            " in (select mt.type_id from material_type mt where mt.pid=?1)" +
            "and m.enable='true'"+
            " ORDER BY m.create_time DESC " +
            " limit ?3 offset ?2", nativeQuery = true)
    List<Material> findAllByTopType(Long typeId, Integer pageNo, Integer pageSize);

    @Query(value = "select  count(id) from material m inner join material_type mt on m.type_id=mt.type_id" +
            " where mt.pid " +
            " in (select mt.type_id from material_type mt where mt.pid=?1)", nativeQuery = true)
    Integer getCountByTopType(Long typeId);

    @Query(value = "select a.attribute_id,a.attr_name from attribute a inner join t_type_attr m on a.attribute_id=m.attribute_id where m.type_id=?1", nativeQuery = true)
    List<Attribute> getMaterialAttributes(Long materialTypeId);
    @Query(value = "select * from material  where type_id=?1",nativeQuery = true)
    List<Material> queryAllByTypeId(Long id);

    @Query(value = "select * from material m inner join material_type mt where mt.type_id in (select mt.type_id from material_type mt where mt.pid=?1) order by create_time DESC limit 1",nativeQuery = true)
    Material lastTimeMaterial(Long id);
    @Query
    Material findByNameAndModel(String name,String model);

    @Query(value="select * from material where id=?1", nativeQuery = true)
   Material findByKey(Long key);

}