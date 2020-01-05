package com.hqhop.modules.system.repository;

import com.hqhop.modules.system.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

/**
 * @author Zheng Jie
 * @date 2018-12-03
 */
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor {

    /**
     * findByName
     * @param name
     * @return
     */
    Role findByName(String name);

    @Query(value = "select sys_role.id,sys_role.create_time,sys_role.data_scope,sys_role.level,sys_role.name,sys_role.remark from sys_role " +
            "inner join users_roles on users_roles.role_id = sys_role.id and users_roles.user_id=?1",nativeQuery = true)
    Set<Role> findByUsers_Id(Long id);

    @Modifying
    @Query(value = "delete from roles_permissions where permission_id = ?1",nativeQuery = true)
    void untiedPermission(Long id);

    @Modifying
    @Query(value = "delete from roles_menus where menu_id = ?1",nativeQuery = true)
    void untiedMenu(Long id);



}
