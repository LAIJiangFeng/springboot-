package com.jf.logistics.dao;

import com.jf.logistics.model.entity.InventoryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRecordDao extends JpaRepository<InventoryRecord,String>, JpaSpecificationExecutor<InventoryRecord> {
    List<InventoryRecord> findByWid(String wid);
    @Query(value = "select id,cid,name,count,company_code,company_name,description,type,update_time,wid from inventory_record where if(?1!='',type=?1,1=1) and if(?2!='',company_code=?2,1=1)",nativeQuery = true)
    List<InventoryRecord> findByTypeAndCompanyCode(int type,String companyCode);
}
