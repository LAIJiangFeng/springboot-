package com.jf.logistics.controller;

import com.jf.logistics.model.entity.Admin;
import com.jf.logistics.model.entity.Sale;
import com.jf.logistics.service.SaleService;
import com.jf.logistics.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/logistics/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_SALE')")
    public Sale save(@RequestBody Sale sale, HttpServletRequest request){
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);

        //登录的用户
        Map<String, Object> map = (Map<String, Object>) JwtTokenUtil.getUser(token);
        //如果非超级管理操作，默认是登录人的公司信息
        if(sale.getCompanyCode()==null||"".equals(sale.getCompanyCode())){
            sale.setCompanyCode((String) map.get("companyCode"));
            sale.setCompanyName((String) map.get("companyName"));
        }
        return saleService.save(sale);
    }

    @DeleteMapping("")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_SALE')")
    public void delete(String id){
        saleService.delete(id);
    }

    @PostMapping("selectSaleList")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_SALE','ROLE_USER')")
    public Map<String, Object> selectSaleList(String cname, String companyName,String companyCode, int page,int pageSize,HttpServletRequest request){
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        //登录的用户
        Map<String, Object> map = (Map<String, Object>) JwtTokenUtil.getUser(token);
        //不是超级管理员公司
        if(!"001".equals(map.get("companyCode"))){
            companyCode=(String) map.get("companyCode");
        }

        String uid=null;
        //用户的话查询自己的商品
        if("user".equals(map.get("type"))){
            uid=(String) map.get("id");
        }
        return saleService.findByCnameAndCompanyNameAndPage(cname,companyName,companyCode,uid,page,pageSize);


    }

}
