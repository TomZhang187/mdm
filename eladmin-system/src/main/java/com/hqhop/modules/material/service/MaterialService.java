package com.hqhop.modules.material.service;

import com.hqhop.modules.material.domain.Attribute;
import com.hqhop.modules.material.domain.Material;
import com.hqhop.modules.material.service.dto.MaterialDTO;
import com.hqhop.modules.material.service.dto.MaterialQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
* @author KinLin
* @date 2019-10-30
*/
//@CacheConfig(cacheNames = "material")
public interface MaterialService {

    /**
    * 查询数据分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable
    Map<String,Object> queryAll(MaterialQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria
    * @return
    */
    //@Cacheable
    List<MaterialDTO> queryAll(MaterialQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    Material findById(Long id);

    /**
     * 新增物料
     * @param material
     * @return
     */
    //@CacheEvict(allEntries = true)
    Material create(Material material);

    /**
     * 编辑
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    Material update(Material resources);

    @Transactional(rollbackFor = Exception.class)
    Material ApprovalUpdate(Material material);

    /**
     * 删除
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);

    @Transactional(rollbackFor = Exception.class)
    void deleteMaterial(Material material);

    /**
     * 通过物料类型查询相关的物料
     * @param typeId
     * @return
     */
    List<Material> queryAllByType(Long typeId, Integer pageNo, Integer pageSize);

    /**
     * 通过类型查找该类型物料数量
     * @param typeId
     * @return
     */
    Integer getCountByTypeId(Long typeId);


    /**
     * 通过大类型查找所有物料
     * @param typePid
     * @return
     */
    List<Material> queryAllByTyPid(Long typePid);
    List<Material> findAllBySecondaryType(Long typeId, Integer pageNo, Integer pageSize);
    Integer getCountBySecondaryType(Long typePid);
    List<Material> findAllByTopType(Long typeId, Integer pageNo, Integer pageSize);
    Integer getCountByTopType(Long typePid);
    List<Attribute> getMaterialAttributes(Long materialTypeId);
    List<Material> findAll();
    List<Material> queryAllByTypeId(Long id);
    Material findByNameAndModel(String name,String model);

    //查询是否有当前用户临时保存的数据
    Material findTemporaryData(Material resources);
    String getWaterCode(Long typeId);
}