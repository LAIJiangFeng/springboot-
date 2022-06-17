package com.jf.logistics.dao;

import com.jf.logistics.model.entity.Distribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistributionDao extends JpaRepository<Distribution,String>, JpaSpecificationExecutor<Distribution> {
    @Query(value = "select * from distribution where if(?1!='',company_code=?1,1=1) and if(?2!='',did=?2,1=1)",nativeQuery = true)
    List<Distribution> findByCompanyCodeAndDid(String companyCode,String did);
}
