package com.hqhop;

import com.hqhop.easyexcels.excelread.MaterialExcelUtils;
import com.hqhop.modules.material.domain.*;
import com.hqhop.modules.material.repository.AttributeRepository;
import com.hqhop.modules.material.repository.MaterialProductionRepository;
import com.hqhop.modules.material.repository.MaterialRepository;
import com.hqhop.modules.material.repository.MaterialTypeRepository;
import com.hqhop.modules.material.service.MaterialDingService;
import com.hqhop.modules.material.service.MaterialService;
import com.hqhop.modules.material.service.impl.AttributeServiceImpl;
import com.hqhop.modules.system.domain.*;
import com.hqhop.modules.system.repository.*;
import com.hqhop.modules.system.service.DeptService;
import com.hqhop.modules.system.service.UserService;
import com.taobao.api.ApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.*;

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

    @Autowired
    private DictDetailRepository dictDetailRepository;

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
        int i = 1;
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

            Material byNameAndModel = null;
            if(object.getStockName() !=null && object.getModel()!=null){
               byNameAndModel = materialRepository.findByNameAndModel(object.getStockName(), object.getModel());


            }
            if (byNameAndModel != null){
                saveMaterialProduction(object, byNameAndModel);
                continue;
            }


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
            if(object.getIdentification()!=null && object.getIdentification().contains(".") &&  type!=null) {
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


    //读取物料整理.xlsx  读取属性

    @Test
    public  void excelTest61() throws
            ApiException {

        int i = 1;
        int j=attributeRepository.getMaxAttributeNumber();


        List<IncMaterialSort2> list = MaterialExcelUtils.readMaterialSort2Excel("D:\\easyExcel\\物料整理.xlsx");
        for (IncMaterialSort2 object : list) {


            if (object.getProperty01() != null && !"-".equals(object.getProperty01())) {

//                if(object.getProperty01().equals("高真空")){
//                    j=j++;
//                    System.out.println("第几次保存1");
//                }


                Attribute attribute = attributeRepository.findByAttributeName(object.getProperty01().trim());
                if (attribute == null) {

                    Attribute newAttribute = new Attribute();
                    newAttribute.setAttributeName(object.getProperty01().trim());
                    newAttribute.setCreateTime(new Timestamp(new Date().getTime()));
                    newAttribute.setAttributeNumber(i++);
                    attributeRepository.save(newAttribute);

                }

            }


            if (object.getProperty02() != null && !"-".equals(object.getProperty02())) {

//                if(object.getProperty02().equals("高真空")){
//                    j=j++;
//                    System.out.println("第几次保存2");
//                }

                Attribute attribute1 = attributeRepository.findByAttributeName(object.getProperty02().trim());
                if (attribute1 == null && object.getProperty02() != null) {

                    Attribute newAttribute = new Attribute();
                    newAttribute.setAttributeName(object.getProperty02().trim());
                    newAttribute.setCreateTime(new Timestamp(new Date().getTime()));
                    newAttribute.setAttributeNumber(i++);
                    attributeRepository.save(newAttribute);

                }
            }


            if (object.getProperty3() != null && !"-".equals(object.getProperty3())) {

//                if(object.getProperty3().equals("高真空")){
//                    j=j++;
//                    System.out.println("第几次保存3");
//                }

                Attribute attribute2 = attributeRepository.findByAttributeName(object.getProperty3().trim());
                if (attribute2 == null && object.getProperty3() != null) {

                    Attribute newAttribute = new Attribute();
                    newAttribute.setAttributeName(object.getProperty3().trim());
                    newAttribute.setCreateTime(new Timestamp(new Date().getTime()));
                    newAttribute.setAttributeNumber(i++);
                    attributeRepository.save(newAttribute);

                }

            }

            if (object.getProperty4() != null && !"-".equals(object.getProperty4())) {


//                if(object.getProperty4().equals("高真空")){
//                    j=j++;
//                    System.out.println("第几次保存4");
//                }

                Attribute attribute3 = attributeRepository.findByAttributeName(object.getProperty4().trim());
                if (attribute3 == null && object.getProperty4() != null) {

                    Attribute newAttribute = new Attribute();
                    newAttribute.setAttributeName(object.getProperty4().trim());
                    newAttribute.setCreateTime(new Timestamp(new Date().getTime()));
                    newAttribute.setAttributeNumber(i++);
                    attributeRepository.save(newAttribute);

                }

            }


            if (object.getProperty5() != null && !"-".equals(object.getProperty5())) {


//                if(object.getProperty5().equals("高真空")){
//                    j=j++;
//                    System.out.println("第几次保存5");
//                }

                Attribute attribute4 = attributeRepository.findByAttributeName(object.getProperty5().trim());
                if (attribute4 == null && object.getProperty5() != null) {

                    Attribute newAttribute = new Attribute();
                    newAttribute.setAttributeName(object.getProperty5().trim());
                    newAttribute.setCreateTime(new Timestamp(new Date().getTime()));
                    newAttribute.setAttributeNumber(i++);
                    attributeRepository.save(newAttribute);

                }

            }

            if (object.getProperty6() != null && !"-".equals(object.getProperty6() )) {


//                if(object.getProperty6().equals("高真空")){
//                    j=j++;
//                    System.out.println("第几次保存6");
//                }

                System.out.println(object.getProperty6());
                Attribute attribute5 = attributeRepository.findByAttributeName(object.getProperty6().trim());
                if (attribute5 == null && object.getProperty6() != null) {

                    Attribute newAttribute = new Attribute();
                    newAttribute.setAttributeName(object.getProperty6().trim());
                    newAttribute.setCreateTime(new Timestamp(new Date().getTime()));
                    newAttribute.setAttributeNumber(i++);
                    attributeRepository.save(newAttribute);

                }

            }


            if (object.getProperty7() != null && !"-".equals(object.getProperty7())) {

//                if(object.getProperty7().equals("高真空")){
//                    j=j++;
//                    System.out.println("第几次保存7");
//                }

                Attribute attribute6 = attributeRepository.findByAttributeName(object.getProperty7());
                if (attribute6 == null && object.getProperty7() != null) {

                    Attribute newAttribute = new Attribute();
                    newAttribute.setAttributeName(object.getProperty7());
                    newAttribute.setCreateTime(new Timestamp(new Date().getTime()));
                    newAttribute.setAttributeNumber(i++);
                    attributeRepository.save(newAttribute);

                }
            }

            if (object.getProperty8() != null && !"-".equals(object.getProperty8())) {


//                if(object.getProperty8().equals("高真空")) {
//                    j = j++;
//                    System.out.println("第几次保存8" );
//                }

                Attribute attribute7 = attributeRepository.findByAttributeName(object.getProperty8().trim());
                if (attribute7 == null && object.getProperty8() != null) {

                    Attribute newAttribute = new Attribute();
                    newAttribute.setAttributeName(object.getProperty8().trim());
                    newAttribute.setCreateTime(new Timestamp(new Date().getTime()));
                    newAttribute.setAttributeNumber(i++);
                    attributeRepository.save(newAttribute);

                }
            }

            if (object.getProperty9() != null && !"-".equals(object.getProperty9())) {


//                if(object.getProperty9().equals("高真空")) {
//                    j = j++;
//                    System.out.println("第几次保存9");
//                }


                Attribute attribute8 = attributeRepository.findByAttributeName(object.getProperty9().trim());
                if (attribute8 == null && object.getProperty9() != null) {

                    Attribute newAttribute = new Attribute();
                    newAttribute.setAttributeName(object.getProperty9().trim());
                    newAttribute.setCreateTime(new Timestamp(new Date().getTime()));
                    newAttribute.setAttributeNumber(i++);
                    attributeRepository.save(newAttribute);

                }
            }


            if (object.getProperty1() != null && !"-".equals(object.getProperty1())) {


//                if(object.getProperty1().equals("高真空")) {
//                    j = j++;
//                    System.out.println("第几次保存1" + j);
//                }

                Attribute attribute9 = attributeRepository.findByAttributeName(object.getProperty1().trim());
                if (attribute9 == null && object.getProperty1() != null) {

                    Attribute newAttribute = new Attribute();
                    newAttribute.setAttributeName(object.getProperty1().trim());
                    newAttribute.setCreateTime(new Timestamp(new Date().getTime()));
                    newAttribute.setAttributeNumber(i++);
                    attributeRepository.save(newAttribute);

                }
            }

        }
    }





    //读取物料整理.xlsx  读取属性与物料类型建立关系
    @Test
    public  void excelTest62() throws
            ApiException {


        List<IncMaterialSort2> list = MaterialExcelUtils.readMaterialSort2Excel("D:\\easyExcel\\物料整理.xlsx");
        for (IncMaterialSort2 object : list) {
            String[] strs = object.getIdentification().split("\\.");
            System.out.println(strs[1]);
            MaterialType type = materialTypeRepository.findByMaterialTypeCode(strs[1]);

            if (type != null) {

                  if(object.getProperty01() !=null){


                Attribute attribute = attributeRepository.findByAttributeName(object.getProperty01());
                if (attribute != null) {

                    if (attributeRepository.findT_Type_att(attribute.getAttributeId(), type.getId()) == 0) {
                        attributeRepository.setTypeAttribute(attribute.getAttributeId(), type.getId());
                    }

                }

                  }


                if(object.getProperty02() !=null) {
                    Attribute attribute1 = attributeRepository.findByAttributeName(object.getProperty02());
                    if (attribute1 != null) {

                        if (attributeRepository.findT_Type_att(attribute1.getAttributeId(), type.getId()) == 0) {
                            attributeRepository.setTypeAttribute(attribute1.getAttributeId(), type.getId());
                        }

                    }
                }


                if(object.getProperty3() !=null) {
                    Attribute attribute2 = attributeRepository.findByAttributeName(object.getProperty3());
                    if (attribute2 != null) {

                        if (attributeRepository.findT_Type_att(attribute2.getAttributeId(), type.getId()) == 0) {
                            attributeRepository.setTypeAttribute(attribute2.getAttributeId(), type.getId());
                        }

                    }

                }

                if(object.getProperty4() !=null) {
                    Attribute attribute3 = attributeRepository.findByAttributeName(object.getProperty4());
                    if (attribute3 != null) {

                        if (attributeRepository.findT_Type_att(attribute3.getAttributeId(), type.getId()) == 0) {
                            attributeRepository.setTypeAttribute(attribute3.getAttributeId(), type.getId());
                        }

                    }
                }


                if(object.getProperty5() !=null) {
                    Attribute attribute4 = attributeRepository.findByAttributeName(object.getProperty5());
                    if (attribute4 != null) {

                        if (attributeRepository.findT_Type_att(attribute4.getAttributeId(), type.getId()) == 0) {
                            attributeRepository.setTypeAttribute(attribute4.getAttributeId(), type.getId());
                        }

                    }
                }


                if(object.getProperty6() !=null) {
                    Attribute attribute5 = attributeRepository.findByAttributeName(object.getProperty6() != null ? object.getProperty6() : "无");
                    if (attribute5 != null) {

                        if (attributeRepository.findT_Type_att(attribute5.getAttributeId(), type.getId()) == 0) {
                            attributeRepository.setTypeAttribute(attribute5.getAttributeId(), type.getId());
                        }

                    }
                }


                if(object.getProperty7() !=null) {
                    Attribute attribute6 = attributeRepository.findByAttributeName(object.getProperty7());
                    if (attribute6 != null) {

                        if (attributeRepository.findT_Type_att(attribute6.getAttributeId(), type.getId()) == 0) {
                            attributeRepository.setTypeAttribute(attribute6.getAttributeId(), type.getId());
                        }

                    }
                }


                if(object.getProperty8() !=null) {
                    Attribute attribute7 = attributeRepository.findByAttributeName(object.getProperty8());
                    if (attribute7 != null) {

                        if (attributeRepository.findT_Type_att(attribute7.getAttributeId(), type.getId()) == 0) {
                            attributeRepository.setTypeAttribute(attribute7.getAttributeId(), type.getId());
                        }

                    }
                }

                if(object.getProperty9() != null) {
                    Attribute attribute8 = attributeRepository.findByAttributeName(object.getProperty9());
                    if (attribute8 != null) {

                        if (attributeRepository.findT_Type_att(attribute8.getAttributeId(), type.getId()) == 0) {
                            attributeRepository.setTypeAttribute(attribute8.getAttributeId(), type.getId());
                        }

                    }
                }


                if(object.getProperty1()!=null){


                Attribute attribute9 = attributeRepository.findByAttributeName(object.getProperty1());
                if (attribute9 != null) {

                    if (attributeRepository.findT_Type_att(attribute9.getAttributeId(), type.getId()) == 0) {
                        attributeRepository.setTypeAttribute(attribute9.getAttributeId(), type.getId());
                    }

                }
            }
            }


        }
    }




    @Test
    public  void test7(){

        String reges = "[0-9.]{9}";

        List<IncMateril2> incMateril2s = MaterialExcelUtils.readMaterial2Sort2Excel("D:\\easyExcel\\物料整理总表2019-12-26-20-52.xlsx");
        for (IncMateril2 object : incMateril2s) {


                MaterialType type = null;
                if(object.getLine3()!=null && object.getLine3().contains(".")){
                    String[] strs = object.getLine3().split("\\.");
                    String typeCode = strs[0]+strs[1];
                    type = materialTypeRepository.findByMaterialTypeCode(typeCode);
                }

            if(type == null){
                continue;
            }


            Material byNameAndModel = null;

            if(object.getLine12()!=null && object.getLine13()!=null){
                 byNameAndModel = materialRepository.findByNameAndModels(object.getLine12(), object.getLine13());
             }

                if(byNameAndModel !=null) {
                    saveMaterialProduction2(object,byNameAndModel );
                    continue;
                }


            Material material = new Material();
                material.setApprovalState("4");
                if(type != null){

                    if(object.getLine6()!=null && object.getLine6().matches(reges)){
                        material.setRemark(object.getLine6());
                    } else {
                        material.setRemark(materialService.getWaterCode(type.getId()));
                    }

                }
                material.setName(object.getLine12());
                material.setModel(object.getLine13());
                material.setUnit(object.getLine14());
                material.setTaxRating(object.getLine15());
                material.setIsTaxable("是".equals(object.getLine16()) ? true : false);
                material.setEnable(true);
                if (type != null){
                    material.setType(type);
                    Material material1 =  materialRepository.save(material);
                    //导入生产档案
                    saveMaterialProduction2(object,material);
                }

                }
            }






//导入生产档案
       public void  saveMaterialProduction2(IncMateril2 object,Material materia) {


           MaterialProduction byOriginalRemark = materialProductionRepository.findByOriginalRemark(object.getLine6());
           if(byOriginalRemark == null){





           MaterialProduction materialProduction = new MaterialProduction();

           if (object.getLine11() != null && object.getLine11().contains(".")) {
               materialProduction.setOriginalRemark(object.getLine11());
           }
           //出库跟踪入库
           materialProduction.setIsOutTrackWarehousing("是".equals(object.getLine17()) ? true : false);
           // 需求管理
           materialProduction.setOutgoingTracking("是".equals(object.getLine18()) ? true : false);
           // 是否需求管理
           materialProduction.setIsDemand("是".equals(object.getLine19()) ? true : false);

           // 是否进行序列号管理
           materialProduction.setIsSerial("是".equals(object.getLine20()) ? true : false);


           //是否批次管理
           materialProduction.setIsBatchManagement("是".equals(object.getLine12()) ? true : false);
           // 是否虚项
           materialProduction.setIsImaginaryTerm("是".equals(object.getLine22()) ? true : false);

           //采购员
               if(object.getLine24()!=null){

                   Employee employee = employeeRepository.findByEmployeeName(object.getLine24());
                   if(employee!=null && employee.getEmployeeCode() != null){
                       materialProduction.setBuyer(employee.getEmployeeCode());
                   }

               }

           materialProduction.setMaterialType(object.getLine25());
               //是否成本对象
               //  是否发料
               //    是否根据检验结果入库
               //   是否免检
               //     是否按生产订单核算成本
               //  是否按成本中心统计产量
               //      是否出入库
               //  是否批次核算
               //委外类型
               materialProduction.setOutsourcingType(object.getLine26());
               materialProduction.setMaterialKenel(object.getLine27());
               // 是否需求合并
               materialProduction.setIsDemandConsolidation("是".equals(object.getLine28()) ? true : false);
               materialProduction.setIsCostObject("是".equals(object.getLine30()) ? true : false);
               materialProduction.setIsHairFeed("是".equals(object.getLine31()) ? true : false);

               materialProduction.setIsInspectionWarehousing("是".equals(object.getLine32()) ? true : false);
               materialProduction.setIsInspect("是".equals(object.getLine33()) ? true : false);
               materialProduction.setIsOrderCost("是".equals(object.getLine34()) ? true : false);
               materialProduction.setIsCenterStatistics("是".equals(object.getLine35()) ? true : false);

               materialProduction.setIsOutgoingWarehousing("是".equals(object.getLine36()) ? true : false);
               materialProduction.setIsBatchesAccount("是".equals(object.getLine37()) ? true : false);

               //计价方式
               materialProduction.setValuationMethod(object.getLine38());
               //最低库存
               materialProduction.setMinStock(object.getLine40());
               //最高库存
               materialProduction.setMaxStock(object.getLine41());
               //在购点
               materialProduction.setAgainBuyPlace(object.getLine42());

               //固定提前日期
               materialProduction.setFixedAdvanceTime(object.getLine44());
               //计划属性
               materialProduction.setPlanningAttribute(object.getLine43());

               //货位
               materialProduction.setZhy(object.getLine45());


               materialProduction.setEnable(true);
               materialProduction.setApprovalState("4");
               if (materia != null) {
                   materialProduction.setMaterial(materia);
               }



               if (object.getLine0() != null) {
                   materialProduction.setDefaultFactory(object.getLine0());
                   materialProductionRepository.save(materialProduction);
               }


           }




       }


       @Test
       public  void test8(){





       }





}
