package com.jf.logistics.service;

import com.jf.logistics.dao.VehicleDao;
import com.jf.logistics.model.entity.Vehicle;
import com.jf.logistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleDao vehicleDao;

    @Override
    public Vehicle save(Vehicle vehicle) {
        vehicle.setCreateAt(DateUtil.getNowTimeString());
        return vehicleDao.save(vehicle);
    }

    @Override
    public List<Vehicle> findByCompanyCodeAndNumberLikeAndTypeLike(String companyCode, String number, String type){
        return vehicleDao.findByCompanyCodeAndNumberLikeAndTypeLike(companyCode,number,type);
    }

    @Override
    public void delete(String id) {
        vehicleDao.deleteById(id);
    }
}
