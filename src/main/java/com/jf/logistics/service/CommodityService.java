package com.jf.logistics.service;

import com.jf.logistics.model.entity.Commodity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CommodityService {
    Commodity save(Commodity commodity);
    Map<String,Object> findCommodityByNameLikeAndPriceAndPage(String name,Double price,String companyCode,String companyName,String userId,int page,int pageSize);
    void deleteCommodity(String id);
    Optional<Commodity> findById(String id);
    List<Commodity> findByCompanyCode(String companyCode);
    List<Commodity> findAll();
}
