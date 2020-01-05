package com.hqhop.modules.company.repository;

import com.hqhop.modules.company.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

/**
 * @author zf
 * @date 2019-10-22
 */
public interface ContactRepository extends JpaRepository<Contact, Long>, JpaSpecificationExecutor {

    Contact findByContactKey(Long key);


    @Query(value = "select contact_key from  contact where company_key = ?1", nativeQuery = true)
    List<BigInteger> getContact_KeyByCompany_Key(Long key);


    @Transactional
    @Modifying
    @Query(value = "delete from contact where contact_key = ?1", nativeQuery = true)
    void deleteByContactKey(Long id);


    /*
     通过名字模糊查询所属公司id集合
    * */
    @Transactional
    @Query(value = "select DISTINCT company_key from contact where name like CONCAT('%',?1,'%')", nativeQuery = true)
    List<BigInteger> findCompany_keyByLikeName(String name);

   Set<Contact> findByCompanyKey(Long key);



    @Query(value = " select * from contact where company_key in (select company_key from company_info WHERE belong_company !=?1)", nativeQuery = true)
   List<Contact> findByNotBelongCompany(String code);




    @Query(value = " select company_name from company_info where company_key in (select company_key from contact WHERE contactKey =?1)", nativeQuery = true)
   String findBelongCompanyName(Long key);



    @Query(value = " select contact_key from contact where company_key in ?1", nativeQuery = true)
    Set<Long> findContactKeysByCompnayKes(List<Long> keys);




}