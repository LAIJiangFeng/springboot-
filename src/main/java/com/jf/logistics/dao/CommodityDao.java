package com.jf.logistics.dao;

import com.jf.logistics.model.entity.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommodityDao extends JpaRepository<Commodity,String>, JpaSpecificationExecutor<Commodity> {
    List<Commodity> findByCompanyCode(String companyCode);
}
