package com.hqhop.modules.material.service.impl;

import com.hqhop.modules.material.service.MaterialDingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author ：张丰
 * @date ：Created in 2019/12/6 0006 9:05
 * @description：物料钉钉业务类
 * @modified By：
 * @version: $
 */


@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MaterialDingServiceImpl implements MaterialDingService {

    //物料生成审批



}
