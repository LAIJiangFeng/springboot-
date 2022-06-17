package com.jf.logistics.service;

import com.jf.logistics.model.entity.InventoryRecord;
import com.jf.logistics.model.vo.CommodityChartVo;

import java.util.List;

public interface InventoryRecordService {
    InventoryRecord save(InventoryRecord record);
    List<InventoryRecord> findByWid(String wid);
    List<CommodityChartVo> inOrOutAnalysis(int type, String companyCode);
}
