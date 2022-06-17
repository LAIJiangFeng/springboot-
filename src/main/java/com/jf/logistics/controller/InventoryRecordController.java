package com.jf.logistics.controller;

import com.jf.logistics.model.entity.InventoryRecord;
import com.jf.logistics.model.vo.CommodityChartVo;
import com.jf.logistics.service.InventoryRecordService;
import com.jf.logistics.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/logistics/inventoryRecord")
public class InventoryRecordController {
    @Autowired
    private InventoryRecordService inventoryRecordService;

    @GetMapping("")
    public List<InventoryRecord> selectByWid(String wid){
        return inventoryRecordService.findByWid(wid);
    }

    @GetMapping("/inOrOutAnalysis")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    public List<CommodityChartVo> inOrOutAnalysis(int type,HttpServletRequest request){
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        //登录的用户
        Map<String, Object> map= (Map<String, Object>) JwtTokenUtil.getUser(token);

        String companyCode=null;
        //不是超级管理员公司
        if(!"001".equals(map.get("companyCode"))){
            companyCode=(String) map.get("companyCode");
        }
        return inventoryRecordService.inOrOutAnalysis(type,companyCode);
    }
}
