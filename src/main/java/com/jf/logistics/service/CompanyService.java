package com.jf.logistics.service;

import com.jf.logistics.model.entity.Company;

import java.util.List;
import java.util.Map;

public interface CompanyService {
    Company save(Company company) throws Exception;
    Map<String,Object> findCompanyByCompanyNameLike(String companyName,int page,int pageSize);
    void delete(String id);
    List<Company> findAll();
}
