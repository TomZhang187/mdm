package com.hqhop;

import com.alipay.api.domain.ItemDiagnoseType;
import com.hqhop.easyExcel.excelRead.MaterialExcelUtils;
import com.hqhop.easyExcel.model.IncMaterial;
import com.hqhop.easyExcel.model.IncMaterialSort;
import com.hqhop.easyExcel.model.IncMaterialType;
import com.hqhop.modules.material.domain.*;
import com.hqhop.modules.material.repository.AttributeRepository;
import com.hqhop.modules.material.repository.MaterialProductionRepository;
import com.hqhop.modules.material.repository.MaterialRepository;
import com.hqhop.modules.material.repository.MaterialTypeRepository;
import com.hqhop.modules.material.service.MaterialDingService;
import com.hqhop.modules.material.service.MaterialService;
import com.hqhop.modules.material.service.impl.AttributeServiceImpl;
import com.hqhop.modules.system.domain.Dept;
import com.hqhop.modules.system.domain.Employee;
import com.hqhop.modules.system.domain.Role;
import com.hqhop.modules.system.domain.User;
import com.hqhop.modules.system.repository.DeptRepository;
import com.hqhop.modules.system.repository.EmployeeRepository;
import com.hqhop.modules.system.repository.RoleRepository;
import com.hqhop.modules.system.repository.UserRepository;
import com.hqhop.modules.system.service.DeptService;
import com.hqhop.modules.system.service.UserService;
import com.hqhop.modules.system.service.dto.UserDTO;
import com.taobao.api.ApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MaterilReadExcel {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DeptService deptService;


    @Autowired
    private MaterialDingService materialDingService;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private AttributeServiceImpl attributeService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private MaterialTypeRepository materialTypeRepository;

    @Autowired
    private MaterialProductionRepository materialProductionRepository;


    @Autowired
    private AttributeRepository attributeRepository;

    @Test
   public  void contextLoads() throws
           ApiException {
        MaterialType materialType = new MaterialType();
        materialType.setCreateTime(new Timestamp(new Date().getTime()));
        materialType.setParentId(0L);
        materialType.setTypeName("sdf");
        materialType.setMaterialTypeCode("001");
        materialTypeRepository.save(materialType);
   }





    //物料类型excel读取工具
    @Test
    public  void excelTest() throws
            ApiException {

        List<IncMaterialType> list = MaterialExcelUtils.readMaterialTypeExcel("D:\\easyExcel\\存货分类.xlsx");
        for (IncMaterialType incMaterialType : list) {
            if(!incMaterialType.getMaterialTypeCode().contains(".")){
                MaterialType materialType = new MaterialType();
                materialType.setCreateTime(new Timestamp(new Date().getTime()));
                materialType.setParentId(1L);
                materialType.setTypeName(incMaterialType.getMaterialClassName());
                materialType.setMaterialTypeCode(incMaterialType.getMaterialTypeCode());
                materialType.setRemark(incMaterialType.getRemark());
                materialTypeRepository.save(materialType);
            }
        }
        for (IncMaterialType incMaterialType : list) {

            if(incMaterialType.getMaterialTypeCode().contains(".")){
                MaterialType materialType = new MaterialType();
                MaterialType materialType1 = materialTypeRepository.findByMaterialTypeCode(incMaterialType.getBigCode());
               if(materialType1 != null){
                   materialType.setCreateTime(new Timestamp(new Date().getTime()));
                   materialType.setParentId(materialType1.getId());
                   materialType.setTypeName(incMaterialType.getMaterialClassName());
                   String[] str = incMaterialType.getMaterialTypeCode().split("\\.");
                   if(str[1].length()==2){
                       str[1] = str[1]+"0";
                   }
                   materialType.setMaterialTypeCode(materialType1.getMaterialTypeCode()+str[1]);
                   materialType.setRemark(incMaterialType.getRemark());
                   materialTypeRepository.save(materialType);
               }
            }
        }
    }




    //物料分类  属性读取
    @Test
    public  void excelTest3() throws
            ApiException {

        List<IncMaterialSort> list = MaterialExcelUtils.readMaterialSortExcel("D:\\easyExcel\\分类属性.xlsx");
        int i=1;
        for (IncMaterialSort object  : list) {

            if(object.getPorperty01() != null &&  !"0".equals(object.getPorperty01())) {

                Attribute attribute1 = attributeRepository.findByAttributeName(object.getPorperty01());
                if (attribute1 == null) {
                    Attribute attribute = new Attribute();
                    attribute.setAttributeName(object.getPorperty01());
                    attribute.setCreateTime(new Timestamp(new Date().getTime()));
                    attribute.setAttributeNumber(i++);
                    Attribute attribute2 = attributeRepository.save(attribute);
                }
            }

            if(object.getPorperty02() != null &&  !"0".equals(object.getPorperty02())) {

                Attribute attribute1 = attributeRepository.findByAttributeName(object.getPorperty02());
                if (attribute1 == null) {
                    Attribute attribute = new Attribute();
                    attribute.setAttributeName(object.getPorperty02());
                    attribute.setCreateTime(new Timestamp(new Date().getTime()));
                    attribute.setAttributeNumber(i++);
                    Attribute attribute2 = attributeRepository.save(attribute);
                }
            }

                if(object.getPorperty3() != null &&  !"0".equals(object.getPorperty3())) {

                    Attribute attribute2 = attributeRepository.findByAttributeName(object.getPorperty3());
                    if (attribute2 == null) {
                        Attribute attribute = new Attribute();
                        attribute.setAttributeName(object.getPorperty3());
                        attribute.setCreateTime(new Timestamp(new Date().getTime()));
                        attribute.setAttributeNumber(i++);
                        attributeRepository.save(attribute);
                    }
                }


            if(object.getPorperty4() != null &&  !"0".equals(object.getPorperty4())) {

                Attribute attribute2 = attributeRepository.findByAttributeName(object.getPorperty4());
                if (attribute2 == null) {
                    Attribute attribute = new Attribute();
                    attribute.setAttributeName(object.getPorperty4());
                    attribute.setCreateTime(new Timestamp(new Date().getTime()));
                    attribute.setAttributeNumber(i++);
                    attributeRepository.save(attribute);
                }
            }


            if(object.getPorperty5() != null &&  !"0".equals(object.getPorperty5())) {

                Attribute attribute2 = attributeRepository.findByAttributeName(object.getPorperty5());
                if (attribute2 == null) {
                    Attribute attribute = new Attribute();
                    attribute.setAttributeName(object.getPorperty5());
                    attribute.setCreateTime(new Timestamp(new Date().getTime()));
                    attribute.setAttributeNumber(i++);
                    attributeRepository.save(attribute);
                }
            }

            if(object.getPorperty6() != null &&  !"0".equals(object.getPorperty6())) {

                Attribute attribute2 = attributeRepository.findByAttributeName(object.getPorperty6());
                if (attribute2 == null) {
                    Attribute attribute = new Attribute();
                    attribute.setAttributeName(object.getPorperty6());
                    attribute.setCreateTime(new Timestamp(new Date().getTime()));
                    attribute.setAttributeNumber(i++);
                    attributeRepository.save(attribute);
                }
            }
            if(object.getPorperty7() != null &&  !"0".equals(object.getPorperty7())) {

                Attribute attribute2 = attributeRepository.findByAttributeName(object.getPorperty7());
                if (attribute2 == null) {
                    Attribute attribute = new Attribute();
                    attribute.setAttributeName(object.getPorperty7());
                    attribute.setCreateTime(new Timestamp(new Date().getTime()));
                    attribute.setAttributeNumber(i++);
                    attributeRepository.save(attribute);
                }
            }

            if(object.getPorperty8() != null &&  !"0".equals(object.getPorperty8())) {

                Attribute attribute2 = attributeRepository.findByAttributeName(object.getPorperty8());
                if (attribute2 == null) {
                    Attribute attribute = new Attribute();
                    attribute.setAttributeName(object.getPorperty8());
                    attribute.setCreateTime(new Timestamp(new Date().getTime()));
                    attribute.setAttributeNumber(i++);
                    attributeRepository.save(attribute);
                }
            }

            if(object.getPorperty9() != null &&  !"0".equals(object.getPorperty9())) {

                Attribute attribute2 = attributeRepository.findByAttributeName(object.getPorperty9());
                if (attribute2 == null) {
                    Attribute attribute = new Attribute();
                    attribute.setAttributeName(object.getPorperty9());
                    attribute.setCreateTime(new Timestamp(new Date().getTime()));
                    attribute.setAttributeNumber(i++);
                    attributeRepository.save(attribute);
                }
            }

            if(object.getPorperty1() != null &&  !"0".equals(object.getPorperty1())) {

                Attribute attribute2 = attributeRepository.findByAttributeName(object.getPorperty1());
                if (attribute2 == null) {
                    Attribute attribute = new Attribute();
                    attribute.setAttributeName(object.getPorperty1());
                    attribute.setCreateTime(new Timestamp(new Date().getTime()));
                    attribute.setAttributeNumber(i++);
                    attributeRepository.save(attribute);
                }
            }

        }


        }



    //读取物料分类Excel   属性与类别建立关系
    @Test
    public  void excelTest4() throws
            ApiException {

        List<IncMaterialSort> list = MaterialExcelUtils.readMaterialSortExcel("D:\\easyExcel\\分类属性.xlsx");
        for (IncMaterialSort object : list) {


                MaterialType type = materialTypeRepository.findByTypeName(object.getSmallClass());

                if(type != null){


                   Attribute attribute = attributeRepository.findByAttributeName(object.getPorperty1()!=null?object.getPorperty1():"无");
                   if(attribute!=null){

                       if(attributeRepository.findT_Type_att(attribute.getAttributeId(),type.getId())==0){
                           attributeRepository.setTypeAttribute(attribute.getAttributeId(),type.getId());
                       }

                   }


                    Attribute attribute1 = attributeRepository.findByAttributeName(object.getPorperty01()!=null?object.getPorperty01():"无");
                    if(attribute1!=null){

                        if(attributeRepository.findT_Type_att(attribute1.getAttributeId(),type.getId())==0){
                            attributeRepository.setTypeAttribute(attribute1.getAttributeId(),type.getId());
                        }

                    }

                    Attribute attribute2 = attributeRepository.findByAttributeName(object.getPorperty02()!=null?object.getPorperty02():"无");
                    if(attribute2!=null){

                        if(attributeRepository.findT_Type_att(attribute2.getAttributeId(),type.getId())==0){
                            attributeRepository.setTypeAttribute(attribute2.getAttributeId(),type.getId());
                        }

                    }

                    Attribute attribute3 = attributeRepository.findByAttributeName(object.getPorperty3()!=null?object.getPorperty3():"无");
                    if(attribute3!=null){

                        if(attributeRepository.findT_Type_att(attribute3.getAttributeId(),type.getId())==0){
                            attributeRepository.setTypeAttribute(attribute3.getAttributeId(),type.getId());
                        }

                    }

                    Attribute attribute4 = attributeRepository.findByAttributeName(object.getPorperty4()!=null?object.getPorperty4():"无");
                    if(attribute4!=null){

                        if(attributeRepository.findT_Type_att(attribute4.getAttributeId(),type.getId())==0){
                            attributeRepository.setTypeAttribute(attribute4.getAttributeId(),type.getId());
                        }

                    }

                    Attribute attribute5 = attributeRepository.findByAttributeName(object.getPorperty5()!=null?object.getPorperty5():"无");
                    if(attribute5!=null){

                        if(attributeRepository.findT_Type_att(attribute5.getAttributeId(),type.getId())==0){
                            attributeRepository.setTypeAttribute(attribute5.getAttributeId(),type.getId());
                        }

                    }

                    Attribute attribute6 = attributeRepository.findByAttributeName(object.getPorperty6()!=null?object.getPorperty6():"无");
                    if(attribute6!=null){

                        if(attributeRepository.findT_Type_att(attribute6.getAttributeId(),type.getId())==0){
                            attributeRepository.setTypeAttribute(attribute6.getAttributeId(),type.getId());
                        }

                    }

                    Attribute attribute7 = attributeRepository.findByAttributeName(object.getPorperty7()!=null?object.getPorperty7():"无");
                    if(attribute7!=null){

                        if(attributeRepository.findT_Type_att(attribute7.getAttributeId(),type.getId())==0){
                            attributeRepository.setTypeAttribute(attribute7.getAttributeId(),type.getId());
                        }

                    }

                    Attribute attribute8 = attributeRepository.findByAttributeName(object.getPorperty8()!=null?object.getPorperty8():"无");
                    if(attribute8!=null){

                        if(attributeRepository.findT_Type_att(attribute8.getAttributeId(),type.getId())==0){
                            attributeRepository.setTypeAttribute(attribute8.getAttributeId(),type.getId());
                        }

                }

                    Attribute attribute9 = attributeRepository.findByAttributeName(object.getPorperty9()!=null?object.getPorperty9():"无");
                    if(attribute9!=null){

                        if(attributeRepository.findT_Type_att(attribute9.getAttributeId(),type.getId())==0){
                            attributeRepository.setTypeAttribute(attribute9.getAttributeId(),type.getId());
                        }

                    }
               }
        }

    }


    //读取物料  生成物料基本档案
    @Test
    public  void excelTest5() throws
            ApiException {


        String reges = "[0-9.]{9}";
        List<IncMaterial> list = MaterialExcelUtils.readMaterialExcel("D:\\easyExcel\\科瑞尔物料属性整理表.xlsx");
        for (IncMaterial object : list) {


            MaterialType type = null;

              if(object.getIdentification()!=null && object.getIdentification().contains(".")){
                  type = materialTypeRepository.findByMaterialTypeCode(object.getIdentification());
                  if(type == null){
                      type = materialTypeRepository.findByTypeName(object.getStockClassification()!=null?object.getStockClassification():"无");
                  }
              }

                Material material = new Material();
                material.setApprovalState("4");
                if(type != null){

                    if(object.getNewCinvcode()!=null && object.getNewCinvcode().matches(reges)){
                        material.setRemark(object.getNewCinvcode());
                    } else {
                        material.setRemark(materialService.getWaterCode(type.getId()));
                    }

                }
                material.setName(object.getStockName());
                material.setModel(object.getModel());
                material.setUnit(object.getMainUnitMeasurement());
                material.setTaxRating(object.getTaxitems());
                material.setIsTaxable("是".equals(object.getIsDutiableService()) ? true : false);
                material.setEnable(true);
                if (type != null){
                    material.setType(type);
                     }
            if(object.getIdentification()!=null && object.getIdentification().contains(".")) {
                Material material1 =  materialRepository.save(material);
                //导入生产档案
                saveMaterialProduction(object,material);
            }
            }
        }

    @Test
      public void test19(){

//        String reges = "[0-9.]{7}";
//        String  str = "001.001";
//          System.out.println(str.matches(reges));
//          String[] strings = str.split("\\.");
//        System.out.println(strings.length);


        List<Material> allByTopType = materialRepository.findAllByTopType(1L, 2, 10);
        for (Material material : allByTopType) {
            System.out.println(material);
        }



      }


        //导入生产档案
        public void  saveMaterialProduction(IncMaterial object,Material materia){


            MaterialProduction materialProduction = new MaterialProduction();

            if(object.getOldCinvcode()!=null && object.getOldCinvcode().contains(".")){
                materialProduction.setOriginalRemark(object.getOldCinvcode());
            }

            materialProduction.setIsOutTrackWarehousing(false);
            materialProduction.setOutgoingTracking(true);
            materialProduction.setIsDemand(true);

            materialProduction.setIsBatchManagement("是".equals(object.getIsBatchManagement())?true:false);
            materialProduction.setIsImaginaryTerm("是".equals(object.getIsImaginaryTerm())?true:false);

            materialProduction.setMaterialType(object.getMaterialType());
            materialProduction.setOutsourcingType(object.getOutsourcingType());
            materialProduction.setMaterialKenel(object.getMaterialKenel());

            materialProduction.setIsDemandConsolidation(true);
            materialProduction.setIsCostObject("是".equals(object.getIsCostObject())?true:false);
            materialProduction.setIsHairFeed(true);

            materialProduction.setIsInspectionWarehousing("是".equals(object.getIsInspectionWarehousing())?true:false);
            materialProduction.setIsInspect("是".equals(object.getIsInspect())?true:false);
            materialProduction.setIsOrderCost("是".equals(object.getIsOrderCost())?true:false);
            materialProduction.setIsCenterStatistics(true);

            materialProduction.setIsOutgoingWarehousing("是".equals(object.getIsOutgoingWarehousing())?true:false);
            materialProduction.setIsBatchesAccount("是".equals(object.getIsBatchesAccount())?true:false);
            materialProduction.setValuationMethod("移动平均");
            materialProduction.setPlanningAttribute(object.getPlanningAttribute());
            materialProduction.setFixedAdvanceTime(object.getFixedAdvanceTime());

            materialProduction.setEnable(true);
            materialProduction.setApprovalState("4");
            if(materia != null){
                materialProduction.setMaterial(materia);
            }

            materialProduction.setDefaultFactory("成都科瑞尔低温设备有限公司");
            materialProductionRepository.save(materialProduction);

        }


    //读取物料  生成物料生产档案
    @Test
    public  void excelTest6() throws
            ApiException {
        List<IncMaterial> list = MaterialExcelUtils.readMaterialExcel("D:\\easyExcel\\科瑞尔物料属性整理表.xlsx");
        for (IncMaterial object : list) {


            Material material = materialRepository.findByRemarkAndModel(object.getOldCinvcode(),object.getModel());

            MaterialProduction materialProduction = new MaterialProduction();

            materialProduction.setIsOutTrackWarehousing(false);
            materialProduction.setOutgoingTracking(true);
            materialProduction.setIsDemand(true);

            materialProduction.setIsBatchManagement("是".equals(object.getIsBatchManagement())?true:false);
            materialProduction.setIsImaginaryTerm("是".equals(object.getIsImaginaryTerm())?true:false);

            materialProduction.setMaterialType(object.getMaterialType());
            materialProduction.setOutsourcingType(object.getOutsourcingType());
            materialProduction.setMaterialKenel(object.getMaterialKenel());

            materialProduction.setIsDemandConsolidation(true);
            materialProduction.setIsCostObject("是".equals(object.getIsCostObject())?true:false);
            materialProduction.setIsHairFeed(true);

            materialProduction.setIsInspectionWarehousing("是".equals(object.getIsInspectionWarehousing())?true:false);
            materialProduction.setIsInspect("是".equals(object.getIsInspect())?true:false);
            materialProduction.setIsOrderCost("是".equals(object.getIsOrderCost())?true:false);
            materialProduction.setIsCenterStatistics(true);

            materialProduction.setIsOutgoingWarehousing("是".equals(object.getIsOutgoingWarehousing())?true:false);
            materialProduction.setIsBatchesAccount("是".equals(object.getIsBatchesAccount())?true:false);
            materialProduction.setValuationMethod("移动平均");
            materialProduction.setPlanningAttribute(object.getPlanningAttribute());
            materialProduction.setFixedAdvanceTime(object.getFixedAdvanceTime());

            materialProduction.setEnable(true);
            materialProduction.setApprovalState("4");
            if(material != null){
                materialProduction.setMaterial(material);
            }

            materialProduction.setDefaultFactory("成都科瑞尔低温设备有限公司");
            materialProductionRepository.save(materialProduction);

        }
    }



}
