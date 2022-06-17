package com.jf.logistics.dao;

import com.jf.logistics.model.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryDao extends JpaRepository<Inventory,String> , JpaSpecificationExecutor<Inventory> {
    Inventory findByWidAndCid(String wid,String cid);
    List<Inventory> findByWid(String wid);
}
