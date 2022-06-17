package com.jf.logistics.service;

import com.jf.logistics.dao.InventoryDao;
import com.jf.logistics.dao.InventoryRecordDao;
import com.jf.logistics.model.entity.Commodity;
import com.jf.logistics.model.entity.Inventory;
import com.jf.logistics.model.entity.InventoryRecord;
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
import java.util.*;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryDao inventoryDao;

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private InventoryRecordDao inventoryRecordDao;

    @Override
    public Inventory in(Inventory inventory) throws Exception {
        inventory.setUpdateTime(DateUtil.getNowTimeString());

        //商品添加库存
        Optional<Commodity> optional = commodityService.findById(inventory.getCid());
        Commodity commodity = optional.get();
        commodity.setCount(commodity.getCount()+inventory.getCount());
        commodityService.save(commodity);

        //为空
        Inventory inventory1 = inventoryDao.findByWidAndCid(inventory.getWid(), inventory.getCid());
        if (inventory1 == null) {
            //新建该商品库存信息
            inventory1 = new Inventory();
            inventory1.setCid(inventory.getCid());
            inventory1.setWid(inventory.getWid());
            inventory1.setCount(0);
            inventory1.setName(inventory.getName());
            inventory1.setUpdateTime(inventory.getUpdateTime());
            inventory1.setCompanyName(inventory.getCompanyName());
            inventory1.setCompanyCode(inventory.getCompanyCode());
            inventory1.setDescription(inventory.getDescription());
        }
        inventory1.setCount(inventory1.getCount() + inventory.getCount());

        //添加入库记录
        InventoryRecord record = new InventoryRecord(null,inventory.getWid(),inventory.getCid(),inventory.getName(),inventory.getCount(),
                inventory.getCompanyCode(),inventory.getCompanyName(),inventory.getUpdateTime(),1,inventory.getDescription());
        inventoryRecordDao.save(record);
        return inventoryDao.save(inventory1);
    }

    @Override
    public Inventory out(Inventory inventory) throws Exception {

        Inventory inv = inventoryDao.findByWidAndCid(inventory.getWid(), inventory.getCid());
        if(inv==null) throw new Exception("仓库不存在该商品");
        if(inv.getCount()-inventory.getCount()<0) throw new Exception("库存不足,该商品剩余"+inv.getCount());
        inv.setCount(inv.getCount()-inventory.getCount());
        inv.setUpdateTime(DateUtil.getNowTimeString());

        //商品添加库存
        Optional<Commodity> optional = commodityService.findById(inventory.getCid());
        Commodity commodity = optional.get();
        commodity.setCount(commodity.getCount()-inventory.getCount());
        commodityService.save(commodity);

        //添加入库记录
        InventoryRecord record = new InventoryRecord(null,inventory.getWid(),inventory.getCid(),inventory.getName(),inventory.getCount(),
              inventory.getCompanyCode(),inventory.getCompanyName(), DateUtil.getNowTimeString(),-1,inventory.getDescription());
        inventoryRecordDao.save(record);

        return  inventoryDao.save(inv);
    }

    @Override
    public Map<String, Object> findByWidAndNameLikeAndPage(String wid, String name, int page, int pageSize) {
        Pageable pageable= PageRequest.of(page-1,pageSize);
        Page<Inventory> data= inventoryDao.findAll(new Specification<Inventory>() {
            @Override
            public Predicate toPredicate(Root<Inventory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                ArrayList<Predicate> list = new ArrayList<>();
                if(name!=null){
                    Predicate p = cb.like(root.get("name"), name + "%");
                    list.add(p);
                }
                if(wid!=null){
                    Predicate p = cb.equal(root.get("wid"), wid );
                    list.add(p);
                }
                Predicate[] p = new Predicate[list.size()];
                Predicate predicate = cb.and(list.toArray(p));
                return predicate;
            }
        },pageable);
        HashMap<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("pageSize",pageSize);
        map.put("list",data.getContent());
        map.put("total",data.getTotalElements());
        return map;
    }

    @Override
    public void delete(String id) {
        inventoryDao.deleteById(id);
    }

    @Override
    public List<Inventory> findAll() {
        return inventoryDao.findAll();
    }

    @Override
    public List<Inventory> findByWid(String wid) {
        return inventoryDao.findByWid(wid);
    }
}
