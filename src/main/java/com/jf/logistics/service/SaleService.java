package com.jf.logistics.service;

import com.jf.logistics.model.entity.Sale;

import java.util.Map;

public interface SaleService {
    Sale save(Sale sale);
    Map<String,Object> findByCnameAndCompanyNameAndPage(String cname,String companyName,String companyCode,String uid,int page,int pageSize);
    void delete(String id);
}
