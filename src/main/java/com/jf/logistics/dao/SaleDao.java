package com.jf.logistics.dao;

import com.jf.logistics.model.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleDao extends JpaRepository<Sale,String>, JpaSpecificationExecutor<Sale> {

}
