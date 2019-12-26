package com.hqhop.modules.material.service.impl;

import com.hqhop.modules.material.domain.MaterialProduction;
import com.hqhop.utils.StringUtils;
import com.hqhop.utils.ValidationUtil;
import com.hqhop.modules.material.repository.MaterialProductionRepository;
import com.hqhop.modules.material.service.MaterialProductionService;
import com.hqhop.modules.material.service.dto.MaterialProductionDTO;
import com.hqhop.modules.material.service.dto.MaterialProductionQueryCriteria;
import com.hqhop.modules.material.service.mapper.MaterialProductionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.hqhop.utils.PageUtil;
import com.hqhop.utils.QueryHelp;
import java.util.List;
import java.util.Map;

/**
* @author wst
* @date 2019-11-26
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MaterialProductionServiceImpl implements MaterialProductionService {

    @Autowired
    private MaterialProductionRepository materialProductionRepository;

    @Autowired
    private MaterialProductionMapper materialProductionMapper;

    @Override
    public Map<String,Object> queryAll(MaterialProductionQueryCriteria criteria, Pageable pageable){
        Page<MaterialProduction> page = materialProductionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(materialProductionMapper::toDto));
    }

    @Override
    public List<MaterialProductionDTO> queryAll(MaterialProductionQueryCriteria criteria){
        return materialProductionMapper.toDto(materialProductionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public MaterialProductionDTO findById(Integer id) {
        Optional<MaterialProduction> materialProduction = materialProductionRepository.findById(id);
        ValidationUtil.isNull(materialProduction,"MaterialProduction","id",id);
        return materialProductionMapper.toDto(materialProduction.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MaterialProductionDTO create(MaterialProduction resources) {
        return materialProductionMapper.toDto(materialProductionRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MaterialProduction resources) {
        Optional<MaterialProduction> optionalMaterialProduction = materialProductionRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalMaterialProduction,"MaterialProduction","id",resources.getId());
        MaterialProduction materialProduction = optionalMaterialProduction.get();
        materialProduction.copy(resources);
        materialProductionRepository.save(materialProduction);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        materialProductionRepository.deleteById(id);
    }

    @Override
    public void sysnToU8Cloud(Integer id) {
        Optional<MaterialProduction> materialProduction = materialProductionRepository.findById(id);
        ValidationUtil.isNull(materialProduction, "MaterialProduction", "id", id);
        MaterialProductionDTO materialProductionDTO = materialProductionMapper.toDto(materialProduction.get());
//        materialProductionDTO.
    }

    private String buildSyncXml(MaterialProductionDTO maDto) {
        StringBuilder builder = new StringBuilder();
        String currentTime = StringUtils.getCurrentTime();
        builder.append("<?xml version=\"1.0\" encoding='UTF-8'?>\n" +
                "<ufinterface account=\"U8cloud\" billtype=\"defStock\" isexchange=\"Y\" proc=\"add\" receiver=\"0001\" replace=\"Y\" roottag=\"\" sender=\"Test\" subbilltype=\"\">")
                .append("  <bill id=\"\">\n <billhead>\n")
                // 存货基本档案（集团）
                // 来源,主数据单据id(若要传存货基本档案 则必填)最大长度为64,类型为:String
                .append("<a_lyid>").append(maDto.getMaterial().getId()).append("</a_lyid>")
                // 公司,固定为集团编码 最大长度为64,类型为:String
                .append("<a_pk_corp>").append("0001").append("</a_pk_corp>")
                // 创建时间,格式（YYYY-MM-DD HH:mm:ss）最大长度为64,类型为:String
                .append("<a_createtime>").append(currentTime).append("</a_createtime>")
                // 创建人,最大长度为64,类型为:String
                .append("<a_creator>").append("主数据平台").append("</a_creator>")
                // 存货编码,必填唯一 最大长度为64,类型为:String
                .append("<a_invcode>").append(maDto.getMaterial().getRemark()).append("</a_invcode>")
                // 存货名称,必填 最大长度为64,类型为:String
                .append("<a_invname>").append(maDto.getMaterial().getName()).append("</a_invname>")
                // 型号,最大长度为64,类型为:String
                .append("<a_invtype>").append(maDto.getMaterial().getModel()).append("</a_invtype>")
                // 是否应税劳务（Y/N）,最大长度为64,类型为:String
                .append("<a_laborflag>").append(maDto.getMaterial().getIsTaxable()?"Y":"N").append("</a_laborflag>")
                // 修改人,最大长度为64,类型为:String
                .append("<a_modifier>").append("主数据平台").append("</a_modifier>")
                // 修改时间,最大长度为64,类型为:String
                .append("<a_modifytime>").append(currentTime).append("</a_modifytime>")
                // 存货分类,必填 对应U8C存货分类档案编码 最大长度为64,类型为:String
                .append("<a_pk_invcl>").append(maDto.getMaterial().getType()).append("</a_pk_invcl>")
                // 主计量单位 必填 对应U8C计量档案编码 ,最大长度为64,类型为:String
                .append("<a_pk_measdoc>").append(maDto.getMaterial().getUnit()).append("</a_pk_measdoc>")
                // 税目,对应U8C税目档案编码 最大长度为64,类型为:String
                .append("<a_pk_taxitems>").append(maDto.getMaterial().getTaxRating()).append("%</a_pk_taxitems>")
                // 存货管理档案
                // 来源id,主数据单据id (若要传存货管理档案 则必填)最大长度为64,类型为:String
                .append("<b_lyid>").append(maDto.getId()).append("</b_lyid>")
                // 创建时间,格式（YYYY-MM-DD HH:mm:ss）最大长度为64,类型为:String
                .append("<b_createtime>").append(currentTime).append("</b_createtime>")
                // 创建人,最大长度为64,类型为:String
                .append("<b_creator>").append("主数据平台").append("</b_creator>")
                // 自定义项（物料类型）,最大长度为64,类型为:String
                .append("<b_free1>").append(maDto.getMaterialKenel()).append("</b_free1>")
                // 自定义项（采购员）,最大长度为64,类型为:String
                .append("<b_free2>").append(maDto.getBuyer()).append("</b_free2>")
                // 自定义项（货位）,最大长度为64,类型为:String
                .append("<b_free3>").append(maDto.getZhy()).append("</b_free3>")
                // 需求管理,最大长度为64,类型为:String
                .append("<b_issalable>").append(maDto.getIsDemand()?"Y":"N").append("</b_issalable>")
                // 是否虚项,最大长度为64,类型为:String
                .append("<b_isvirtual>").append(maDto.getIsImaginaryTerm()?"Y":"N").append("</b_isvirtual>")
                // 修改人,最大长度为64,类型为:String
                .append("<b_modifier>").append("").append("</b_modifier>")
                // 修改时间,最大长度为64,类型为:String
                .append("<b_modifytime></b_modifytime>")
                // 出库跟踪入库,最大长度为64,类型为:String
                .append("<b_outtrackin>").append(maDto.getOutgoingTracking()?"Y":"N").append("</b_outtrackin>")
                // 公司,必填 对应U8C公司目录档案编码 最大长度为64,类型为:String
                .append("<b_pk_corp>").append(maDto.getDefaultFactory()).append("</b_pk_corp>")
                // 默认工厂,最大长度为64,类型为:String
                .append("<b_pk_dftfactory>").append(maDto.getDefaultFactory()).append("</b_pk_dftfactory>")
                // 封存时间,最大长度为64,类型为:String
                .append("<b_sealdate></b_sealdate>")
                // 是否封存,最大长度为64,类型为:String
                .append("<b_sealflag>").append("N").append("</b_sealflag>")
                // 是否进行序列号管理,最大长度为64,类型为:String
//                .append("<b_serialmanaflag>").append(maDto.get).append("</b_serialmanaflag>")
                // 是否批次管理,最大长度为64,类型为:String
//                .append("<b_wholemanaflag>").append(maDto.getisb).append("</b_wholemanaflag>")
                // 物料生产档案（公司）案
                // 来源id,主数据单据id (若要传物料生产档案 则必填)最大长度为64,类型为:String
                .append("<c_lyid></c_lyid>")
                // 是否免检,最大长度为64,类型为:String
                .append("<c_chkfreeflag>N</c_chkfreeflag>")
                // 是否需求合并,最大长度为64,类型为:String
                .append("<c_combineflagc>N</c_combineflagc>")
                // 创建日期,最大长度为64,类型为:String
                .append("<c_createtimec></c_createtimec>")
                // 创建人,最大长度为64,类型为:String
                .append("<c_creator>HLLO</c_creator>")
                // 是否按生产订单核算成本,最大长度为64,类型为:String
                .append("<c_iscostbyorder>N</c_iscostbyorder>")
                // c_isctoutput,最大长度为64,类型为:String
                .append("<c_isctoutput>N</c_isctoutput>")
                // 是否发料,最大长度为64,类型为:String
                .append("<c_issend>N</c_issend>")
                // 是否出入库,最大长度为64,类型为:String
                .append("<c_isused>N</c_isused>")
                // 最低库存,最大长度为64,类型为:String
                .append("<c_lowstocknum>21</c_lowstocknum>")
                // 物料型态,最大长度为64,类型为:String
                .append("<c_materstate>02</c_materstate>")
                // 物料类型,最大长度为64,类型为:String
                .append("<c_matertyp>02</c_matertyp>")
                // 最高库存,最大长度为64,类型为:String
                .append("<c_maxstornum>3</c_maxstornum>")
// 修改人,最大长度为64,类型为:String
                .append("<c_modifier></c_modifier>")
// 修改时间,最大长度为64,类型为:String
                .append("<c_modifytime></c_modifytime>")
// 委外类型,最大长度为64,类型为:String
                .append("<c_outtype>1</c_outtype>")
// 公司,必填 对应U8C公司目录档案编码 最大长度为64,类型为:String
                .append("<c_pk_corp>1002</c_pk_corp>")
// 生产部门,最大长度为64,类型为:String
                .append("<c_pk_deptdoc3>01</c_pk_deptdoc3>")
// 生产业务员,最大长度为64,类型为:String
                .append("<c_pk_psndoc3c></c_pk_psndoc3c>")
// 封存人,最大长度为64,类型为:String
                .append("<c_pk_sealuser></c_pk_sealuser>")
// 计价方式,最大长度为64,类型为:String
                .append("<c_pricemethod>1</c_pricemethod>")
// 安全库存,最大长度为64,类型为:String
                .append("<c_safetystocknum>1</c_safetystocknum>")
// 计划属性,最大长度为64,类型为:String
                .append("<c_scheattr>>MRP</c_scheattr>")
// 封存时间,最大长度为64,类型为:String
                .append("<c_sealdate></c_sealdate>")
// 封存标志,最大长度为64,类型为:String
                .append("<c_sealflag>N</c_sealflag>")
// 是否成本对象,最大长度为64,类型为:String
                .append("<c_sfcbdx>N</c_sfcbdx>")
// 是否批次核算,最大长度为64,类型为:String
                .append("<c_sfpchs>N</c_sfpchs>")
                // 是否根据检验结果入库,最大长度为64,类型为:String
                .append("<c_stockbycheck>N</c_stockbycheck>")
            // 是否虚项,最大长度为64,类型为:String
                .append("<c_virtualflag>N</c_virtualflag>")
// 再定货点,最大长度为64,类型为:String
                .append("<c_zdhd>1</c_zdhd>")
// 库存组织主键,最大长度为64,类型为:String
                .append("<c_pk_calbody>1002</c_pk_calbody>")
// 供应类型,最大长度为64,类型为:String)
                .append("<c_supplytype>0</c_supplytype>")
                .append(" </billhead>\n</bill>\n")
                .append("</ufinterface>");
        return builder.toString();
    }
}
