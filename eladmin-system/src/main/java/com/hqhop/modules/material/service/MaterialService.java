package com.hqhop.modules.material.service;

import com.hqhop.modules.material.domain.Material;
import com.hqhop.modules.material.service.dto.MaterialDTO;
import com.hqhop.modules.material.service.dto.MaterialQueryCriteria;
import org.springframework.data.domain.Pageable;

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
    void update(Material resources);

    /**
     * 删除
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);

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
}