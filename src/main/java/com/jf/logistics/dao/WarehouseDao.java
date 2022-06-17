package com.jf.logistics.dao;

import com.jf.logistics.model.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseDao extends JpaRepository<Warehouse,String>, JpaSpecificationExecutor<Warehouse> {
    @Query(value = "select id,company_code,company_name,name,phone,principle,update_at from " +
            "warehouse where if(?1!='',company_code=?1 ,1=1)and if(?2!='',name like concat(?2,'%'),1=1)",nativeQuery = true)
    List<Warehouse> findWarehouseByCompanyCodeAndNameLike(String companyCode,String name);
}
