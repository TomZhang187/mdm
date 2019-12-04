package com.hqhop.modules.system.service.impl;

import com.hqhop.modules.system.domain.Dict;
import com.hqhop.modules.system.domain.DictDetail;
import com.hqhop.modules.system.repository.DictRepository;
import com.hqhop.modules.system.service.dto.DictDetailQueryCriteria;
import com.hqhop.utils.PageUtil;
import com.hqhop.utils.QueryHelp;
import com.hqhop.utils.ValidationUtil;
import com.hqhop.modules.system.repository.DictDetailRepository;
import com.hqhop.modules.system.service.DictDetailService;
import com.hqhop.modules.system.service.dto.DictDetailDTO;
import com.hqhop.modules.system.service.mapper.DictDetailMapper;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
@SuppressWarnings("ALL")
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DictDetailServiceImpl implements DictDetailService {

    @Autowired
    private DictDetailRepository dictDetailRepository;

    @Autowired
    private DictRepository dictRepository;

    @Autowired
    private DictDetailMapper dictDetailMapper;

    @Override
    public Map queryAll(DictDetailQueryCriteria criteria, Pageable pageable) {
        Page<DictDetail> page = dictDetailRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(dictDetailMapper::toDto));
    }

    @Override
    public DictDetailDTO findById(Long id) {
        Optional<DictDetail> dictDetail = dictDetailRepository.findById(id);
        ValidationUtil.isNull(dictDetail,"DictDetail","id",id);
        return dictDetailMapper.toDto(dictDetail.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictDetailDTO create(DictDetail resources) {
        return dictDetailMapper.toDto(dictDetailRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DictDetail resources) {
        Optional<DictDetail> optionalDictDetail = dictDetailRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalDictDetail,"DictDetail","id",resources.getId());
        DictDetail dictDetail = optionalDictDetail.get();
        resources.setId(dictDetail.getId());
        dictDetailRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        dictDetailRepository.deleteById(id);
    }

    /*
    返回value-label Map集合
    * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map getLabelByValue(DictDetailQueryCriteria criteria) {
                criteria.setDictName(criteria.getDictName().trim());
        List<DictDetail> list = dictDetailRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder));
        HashMap map = new HashMap();
        for (DictDetail dictDetail : list) {
                 map.put(dictDetail.getValue(),dictDetail.getLabel());
        }

          return  map;
        }


        /*
       返回label-value Map集合对象
        * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map getValueByLabel(DictDetailQueryCriteria criteria) {
        criteria.setDictName(criteria.getDictName().trim());
        List<DictDetail> list = dictDetailRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder));
        HashMap map = new HashMap();
        for (DictDetail dictDetail : list) {
            map.put(dictDetail.getLabel(),dictDetail.getValue());
        }
        return  map;
    }

    /*
    拿到字典标签通过字典值
    * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getDicLabel(String dictName, Integer value) {
        DictDetailQueryCriteria criteria = new DictDetailQueryCriteria();
        criteria.setDictName(dictName.trim());
        criteria.setValue(value.toString());
        List<DictDetail> list = dictDetailRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder));
       if(list.isEmpty()){
           return null;
       }else {
           return  list.get(0).getLabel();
       }
    }
/* 拿到字典值通过字典标签
* */
    @Override
    public String getDicValue(String dictName, String label) {

        Dict dict = dictRepository.findByName(dictName);

        DictDetail dictDetail = dictDetailRepository.findByLabelAndDict_Id(label,dict.getId());

        return  dictDetail.getValue();

    }


}
