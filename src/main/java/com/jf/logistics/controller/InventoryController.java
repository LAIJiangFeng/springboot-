package com.jf.logistics.controller;

import com.jf.logistics.model.entity.Inventory;
import com.jf.logistics.service.InventoryService;
import com.jf.logistics.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/logistics/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/in")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_WAREHOUSE')")
    public Inventory in(@RequestBody Inventory inventory, HttpServletRequest request) throws Exception {
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        //登录的用户
        Map<String, Object> map= (Map<String, Object>) JwtTokenUtil.getUser(token);
        inventory.setCompanyCode ((String) map.get("companyCode"));
        inventory.setCompanyName ((String) map.get("companyName"));
        return inventoryService.in(inventory);
    }

    @PostMapping("/out")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_WAREHOUSE')")
    public Inventory out(@RequestBody Inventory inventory,HttpServletRequest request) throws Exception {
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        //登录的用户
        Map<String, Object> map= (Map<String, Object>) JwtTokenUtil.getUser(token);
        inventory.setCompanyCode ((String) map.get("companyCode"));
        inventory.setCompanyName ((String) map.get("companyName"));
        return inventoryService.out(inventory);
    }

    @PostMapping("/selectInventoryList")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_WAREHOUSE')")
    public Map<String, Object> selectInventoryList(String wid, String name, int page, int pageSize){
        return inventoryService.findByWidAndNameLikeAndPage(wid, name, page, pageSize);
    }

    @GetMapping("")
    public List<Inventory> selectByWid(String wid){
        return inventoryService.findByWid(wid);
    }

}
