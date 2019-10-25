package com.hqhop.modules.company.repository;

import com.hqhop.modules.company.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

/**
 * @author zf
 * @date 2019-10-22
 */
public interface ContactRepository extends JpaRepository<Contact, Long>, JpaSpecificationExecutor {


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


}