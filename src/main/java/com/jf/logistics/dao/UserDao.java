package com.jf.logistics.dao;

import com.jf.logistics.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {
    User findUserByUsername(String username);
    boolean existsUserByUsername(String username);

    @Query(value = "select id,company_code,company_name,create_at,password,roles,type,username,status,name from user " +
            "where if(?1!='',company_code=?1,1=1) and if(?2!='',type =?2,1=1)",nativeQuery = true)
    List<User> findByCompanyCodeAndType(String companyCode,String type);
}
