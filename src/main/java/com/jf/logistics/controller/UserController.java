package com.jf.logistics.controller;

import com.jf.logistics.model.entity.Admin;
import com.jf.logistics.model.entity.User;
import com.jf.logistics.service.UserService;
import com.jf.logistics.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/logistics/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    public User save(@RequestBody User user, HttpServletRequest request) throws Exception {
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
            //登录的用户
            Map<String, Object> map = (Map<String, Object>) JwtTokenUtil.getUser(token);
            //如果非超级管理操作，默认是登录人的公司信息
            if(user.getCompanyCode()==null||"".equals(user.getCompanyCode())){
                user.setCompanyCode((String) map.get("companyCode"));
                user.setCompanyName((String) map.get("companyName"));
            }
        return userService.save(user);
    }

    @DeleteMapping("")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    public void delete(String id){
        userService.deleteUser(id);
    }

    @PostMapping("/selectUserList")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    public Map<String, Object> selectUserList(String name,String username, String type, String companyCode, String companyName, int page, int pageSize, HttpServletRequest request){
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        //登录的用户
        Map<String, Object> map= (Map<String, Object>) JwtTokenUtil.getUser(token);
        //不是超级管理员公司
        if(!"001".equals(map.get("companyCode"))){
            companyCode= (String) map.get("companyCode");
        }

        return userService.findAllByUsernameLikeAndTypeAndPage(name,username,type,companyCode,companyName,page,pageSize);
    }

    @GetMapping("")
    public List<User> getDriver(HttpServletRequest request) {
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        //登录的用户
        Map<String, Object> map= (Map<String, Object>) JwtTokenUtil.getUser(token);
        String companyCode=null;
        //不是超级管理员公司
        if(!"001".equals(map.get("companyCode"))){
            companyCode= (String) map.get("companyCode");
        }
        return userService.findByCompanyCodeAndType(companyCode,"driver");
    }

    @GetMapping("/getUser")
    public List<User> getUser(HttpServletRequest request) {
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        //登录的用户
        Map<String, Object> map= (Map<String, Object>) JwtTokenUtil.getUser(token);
        String companyCode=null;
        //不是超级管理员公司
        if(!"001".equals(map.get("companyCode"))){
            companyCode= (String) map.get("companyCode");
        }
        return userService.findByCompanyCodeAndType(companyCode,"user");
    }
}
