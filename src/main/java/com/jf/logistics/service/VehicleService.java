package com.jf.logistics.service;

import com.jf.logistics.model.entity.Vehicle;

import java.util.List;
import java.util.Map;

public interface VehicleService {
    Vehicle save(Vehicle vehicle);
    List<Vehicle> findByCompanyCodeAndNumberLikeAndTypeLike(String companyCode, String number, String type);
    void delete(String id);
}
