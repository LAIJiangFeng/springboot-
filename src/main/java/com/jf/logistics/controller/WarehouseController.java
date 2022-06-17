package com.jf.logistics.controller;

import com.jf.logistics.model.entity.Warehouse;
import com.jf.logistics.service.WarehouseService;
import com.jf.logistics.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("logistics/warehouse")
public class WarehouseController {
    @Autowired
    private WarehouseService warehouseService;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_WAREHOUSE')")
    public Warehouse save(@RequestBody Warehouse warehouse, HttpServletRequest request){
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        //登录的用户
        Map<String, Object> map = (Map<String, Object>) JwtTokenUtil.getUser(token);
        //如果非超级管理操作，默认是登录人的公司信息
        if(warehouse.getCompanyCode()==null||"".equals(warehouse.getCompanyCode())){
            warehouse.setCompanyCode((String) map.get("companyCode"));
            warehouse.setCompanyName((String) map.get("companyName"));
        }
        return warehouseService.save(warehouse);
    }

    @PostMapping("/selectWarehouseList")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_WAREHOUSE')")
    public List<Warehouse> selectWarehouseList(String companyCode, String name, HttpServletRequest request){
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        //登录的用户
        Map<String, Object> map = (Map<String, Object>) JwtTokenUtil.getUser(token);
        //不是超级管理员公司
        if(!"001".equals(map.get("companyCode"))){
            companyCode= (String) map.get("companyCode");
        }
        return warehouseService.selectWarehouseList(companyCode,name);
    }

    @DeleteMapping("")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_WAREHOUSE')")
    public void delete(String id){
        warehouseService.delete(id);
    }
}
