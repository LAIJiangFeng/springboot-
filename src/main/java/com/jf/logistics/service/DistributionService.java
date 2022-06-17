package com.jf.logistics.service;

import com.jf.logistics.model.dto.DistributionDto;
import com.jf.logistics.model.entity.Distribution;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface DistributionService {
    Distribution save(Distribution distribution) throws Exception;
    Map<String,Object> findByDistributionDto(DistributionDto distributionDto);
    void delete(String id);
    Distribution findById(String id);
    Map<String,String> getLocation(Double latitude, Double longitude, String address);
    List<Distribution> findByCompanyCodeAndDid(String companyCode,String did);
}
