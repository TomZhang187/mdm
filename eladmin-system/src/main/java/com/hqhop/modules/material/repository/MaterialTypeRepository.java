package com.hqhop.modules.material.repository;

import com.hqhop.modules.material.domain.MaterialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author KinLin
 * @date 2019-10-30
 * 物料类型管理dao
 */
public interface MaterialTypeRepository extends JpaRepository<MaterialType, Long>, JpaSpecificationExecutor {

    @Query(value = "select type_name from material_type where type_id = ?1", nativeQuery = true)
    String findNameById(Long id);
    List<MaterialType> findByParentId(Long id);

    @Query(value = "select * from material_type where type_name = ?1", nativeQuery = true)
    MaterialType findByTypeName(String typeName);



    @Query(value = "select * from material_type where type_id = ?1", nativeQuery = true)
    MaterialType getOne(Long id);

    MaterialType findByMaterialTypeCode(String code);




}