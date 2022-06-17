package com.jf.logistics.controller;

import com.jf.logistics.model.dto.DistributionDto;
import com.jf.logistics.model.entity.Commodity;
import com.jf.logistics.model.entity.Distribution;
import com.jf.logistics.service.DistributionService;
import com.jf.logistics.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/logistics/distribution")
public class DistributionController {

    @Autowired
    private DistributionService distributionService;


    @PostMapping("")
    public Distribution save(@RequestBody Distribution distribution, HttpServletRequest request) throws Exception {
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);

        //登录的用户
        Map<String, Object> map = (Map<String, Object>) JwtTokenUtil.getUser(token);
        //如果非超级管理操作，默认是登录人的公司信息
        if(distribution.getCompanyCode()==null||"".equals(distribution.getCompanyCode())){
            distribution.setCompanyCode((String) map.get("companyCode"));
            distribution.setCompanyName((String) map.get("companyName"));
        }

        //用户的话保存id
        if("user".equals(map.get("type"))){
            distribution.setUid ((String) map.get("id"));
        }
        return distributionService.save(distribution);
    }

    @DeleteMapping("")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_DISTRIBUTION')")
    public void delete(String id){
        distributionService.delete(id);
    }

    @PostMapping("/selectDistributionList")
    public Map<String, Object> selectDistributionList(@RequestBody DistributionDto distributionDto, HttpServletRequest request){

        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        //登录的用户
        Map<String, Object> map= (Map<String, Object>) JwtTokenUtil.getUser(token);
        //不是超级管理员公司
        if(!"001".equals(map.get("companyCode"))){
            distributionDto.setCompanyCode((String) map.get("companyCode"));
        }

        //用户的话查询自己的商品
        if("user".equals(map.get("type"))){
           distributionDto.setUid((String) map.get("id"));
        }

        //司机的话查询自己配送的商品
        if("driver".equals(map.get("type"))){
            distributionDto.setDid((String) map.get("id"));
        }

        return  distributionService.findByDistributionDto(distributionDto);
    }

    @GetMapping ("")
    public Distribution selectById(String id, HttpServletRequest request) throws Exception {
        return distributionService.findById(id);
    }

    @PostMapping("/getLocation")
    public Map<String,String> getLocation(Double latitude,Double longitude,String address,HttpServletRequest request) {
        return distributionService.getLocation(latitude,longitude,address);
    }

    @GetMapping("/selectDistributionList")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_LOCATION')")
    public List<Distribution> selectDistributionList(HttpServletRequest request){
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        //登录的用户
        Map<String, Object> map= (Map<String, Object>) JwtTokenUtil.getUser(token);
        String companyCode=null;
        String did=null;
        //不是超级管理员公司
        if(!"001".equals(map.get("companyCode"))){
            companyCode=(String) map.get("companyCode");
        }

        //司机的话查询自己配送的商品
        if("driver".equals(map.get("type"))){
            did=(String) map.get("id");
        }

        return distributionService.findByCompanyCodeAndDid(companyCode,did);
    }

}
