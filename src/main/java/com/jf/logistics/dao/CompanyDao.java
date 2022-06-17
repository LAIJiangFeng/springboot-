package com.jf.logistics.dao;

import com.jf.logistics.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyDao extends JpaRepository<Company,String>, JpaSpecificationExecutor<Company> {
    boolean existsCompanyByCompanyCode(String companyCode);

}
