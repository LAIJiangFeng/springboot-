package com.jf.logistics.controller;

import com.jf.logistics.model.entity.Vehicle;
import com.jf.logistics.service.VehicleService;
import com.jf.logistics.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/logistics/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    public Vehicle save(@RequestBody Vehicle vehicle, HttpServletRequest request){
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        //登录的用户
        Map<String, Object> map = (Map<String, Object>) JwtTokenUtil.getUser(token);
        //如果非超级管理操作，默认是登录人的公司信息
        if(vehicle.getCompanyCode()==null||"".equals(vehicle.getCompanyCode())){
            vehicle.setCompanyCode((String) map.get("companyCode"));
            vehicle.setCompanyName((String) map.get("companyName"));
        }
        return vehicleService.save(vehicle);
    }

    @PostMapping("/selectVehicleList")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_USER')")
    public List<Vehicle> selectVehicleList(String companyCode, String search, HttpServletRequest request){
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        //登录的用户
        Map<String, Object> map = (Map<String, Object>) JwtTokenUtil.getUser(token);
        //不是超级管理员公司
        if(!"001".equals(map.get("companyCode"))){
            companyCode= (String) map.get("companyCode");
        }
        return vehicleService.findByCompanyCodeAndNumberLikeAndTypeLike(companyCode,search,search);
    }

    @DeleteMapping("")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    public void delete(String id){
       vehicleService.delete(id);
    }

}
