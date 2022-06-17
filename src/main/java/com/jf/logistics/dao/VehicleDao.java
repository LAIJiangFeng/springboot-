package com.jf.logistics.dao;

import com.jf.logistics.model.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleDao extends JpaRepository<Vehicle,String>, JpaSpecificationExecutor<Vehicle> {
    @Query(value = "select id,number,type,driving,create_at,company_name,company_code from Vehicle where if(?1!='',company_code=?1,1=1)" +
            "and (if(?2!='',number like concat(?2 ,'%'),1=1) or if(?3!='', type like concat(?3,'%'),1=1))",nativeQuery = true)
    List<Vehicle> findByCompanyCodeAndNumberLikeAndTypeLike(String companyCode,String number,String type);

}
