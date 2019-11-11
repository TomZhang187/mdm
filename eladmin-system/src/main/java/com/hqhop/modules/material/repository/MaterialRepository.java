package com.hqhop.modules.material.repository;

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
     * @param typeId
     * @return
     */
    @Query(value = "select * from material m inner join material_type mt on m.type_id=mt.type_id" +
                                            " where m.type_id=?1" +
                    " ORDER BY m.create_time DESC " +
                    " limit ?3 offset ?2",nativeQuery = true)
    List<Material> findAllByType(Long typeId, Integer pageNo, Integer pageSize);

    @Query(value = "select count(id) from material m inner join material_type mt on m.type_id=mt.type_id " +
                    " where m.type_id=?1",nativeQuery = true)
    Integer getCountByTypeId(Long typeId);

}