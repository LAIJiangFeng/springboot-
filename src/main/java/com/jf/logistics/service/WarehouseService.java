package com.jf.logistics.service;

import com.jf.logistics.model.entity.Warehouse;

import java.util.List;
import java.util.Map;

public interface WarehouseService {
    Warehouse save(Warehouse warehouse);
    List<Warehouse> selectWarehouseList(String companyCode,String name);
    void delete(String id);
}
