package com.jf.logistics.dao;

import com.jf.logistics.model.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *  by+属性名+查询条件+连接符+属性名+查询条件
 */
@Repository
public interface AdminDao extends JpaRepository<Admin,String> , JpaSpecificationExecutor<Admin> {
    Admin findAdminByUsernameAndPassword(String username,String password);
    Admin findAdminByUsername(String username);
    boolean existsAdminByUsername(String username);
    boolean existsAdminByRoles(String roles);

}
