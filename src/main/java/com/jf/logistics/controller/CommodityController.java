package com.jf.logistics.controller;

import com.jf.logistics.model.entity.Commodity;

import com.jf.logistics.service.CommodityService;
import com.jf.logistics.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/logistics/commodity")
public class CommodityController {
    @Autowired
    private CommodityService commodityService;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_COMMODITY')")
    public Commodity save(@RequestBody Commodity commodity, HttpServletRequest request) throws Exception {
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        //登录的用户
        Map<String, Object> map = (Map<String, Object>) JwtTokenUtil.getUser(token);
        //如果非超级管理操作，默认是登录人的公司信息
        if(commodity.getCompanyCode()==null||"".equals(commodity.getCompanyCode())){
            commodity.setCompanyCode((String) map.get("companyCode"));
            commodity.setCompanyName((String) map.get("companyName"));
        }

        //用户的话保存id
        if("user".equals(map.get("type"))){
            commodity.setUserId ((String) map.get("id"));
        }
        return commodityService.save(commodity);
    }

    @DeleteMapping("")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_COMMODITY')")
    public void delete(String id){
        commodityService.deleteCommodity(id);
    }

    @PostMapping("/selectCommodityList")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_COMMODITY','ROLE_USER')")
    public Map<String, Object> selectCommodityList(String name, Double price, String companyCode, String companyName, int page, int pageSize, HttpServletRequest request){
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        //登录的用户
        Map<String, Object> map= (Map<String, Object>) JwtTokenUtil.getUser(token);
        //不是超级管理员公司
        if(!"001".equals(map.get("companyCode"))){
            companyCode= (String) map.get("companyCode");
        }

        String userId=null;
        //用户的话查询自己的商品
        if("user".equals(map.get("type"))){
            userId= (String) map.get("id");
        }

        return  commodityService.findCommodityByNameLikeAndPriceAndPage(name,price,companyCode,companyName,userId,page,pageSize);
    }

    @PostMapping("/findByCompanyCode")
    public List<Commodity> findByCompanyCode(HttpServletRequest request) {
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        //登录的用户
        Map<String, Object> map= (Map<String, Object>) JwtTokenUtil.getUser(token);
        List<Commodity> list=null;
        //不是超级管理员公司
        if(!"001".equals(map.get("companyCode"))){
            String companyCode= (String) map.get("companyCode");
            list = commodityService.findByCompanyCode(companyCode);
        }else {
            list = commodityService.findAll();
        }
        return list;
    }
}
