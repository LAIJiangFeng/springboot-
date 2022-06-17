package com.jf.logistics.service;

import com.jf.logistics.model.entity.Inventory;

import java.util.List;
import java.util.Map;


public interface InventoryService {
    Inventory in(Inventory inventory) throws Exception;
    Inventory out(Inventory inventory) throws Exception;
    Map<String,Object> findByWidAndNameLikeAndPage(String wid,String name,int page, int pageSize);
    void delete(String id);
    List<Inventory> findAll();
    List<Inventory> findByWid(String wid);
}
