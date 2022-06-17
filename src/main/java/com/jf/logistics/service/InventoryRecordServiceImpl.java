package com.jf.logistics.service;

import com.jf.logistics.dao.InventoryRecordDao;
import com.jf.logistics.model.entity.InventoryRecord;
import com.jf.logistics.model.vo.CommodityChartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class InventoryRecordServiceImpl implements InventoryRecordService {
    @Autowired
    private InventoryRecordDao inventoryRecordDao;
    @Override
    public InventoryRecord save(InventoryRecord record) {
        return inventoryRecordDao.save(record);
    }

    @Override
    public List<InventoryRecord> findByWid(String wid) {
        return inventoryRecordDao.findByWid(wid);
    }

    @Override
    public List<CommodityChartVo> inOrOutAnalysis(int type, String companyCode) {
        List<CommodityChartVo> result = new ArrayList<>();
        List<InventoryRecord> list = inventoryRecordDao.findByTypeAndCompanyCode(type,companyCode);
        HashMap<String, Integer> map = new HashMap<>();
        for(InventoryRecord r:list){
            map.put(r.getName(),map.getOrDefault(r.getName(),0)+r.getCount());
        }
        for (String key : map.keySet()) {
            result.add(new CommodityChartVo(map.get(key), key));
        }
        return result;
    }


}
