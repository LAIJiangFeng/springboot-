package com.jf.logistics.service;

import com.jf.logistics.dao.WarehouseDao;
import com.jf.logistics.model.entity.Warehouse;
import com.jf.logistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    private WarehouseDao warehouseDao;

    @Override
    public Warehouse save(Warehouse warehouse) {
        warehouse.setUpdateAt(DateUtil.getNowTimeString());
        return warehouseDao.save(warehouse);
    }

    @Override
    public List<Warehouse> selectWarehouseList(String companyCode, String name) {
        return warehouseDao.findWarehouseByCompanyCodeAndNameLike(companyCode,name);
    }

    @Override
    public void delete(String id) {
      warehouseDao.deleteById(id);
    }
}
