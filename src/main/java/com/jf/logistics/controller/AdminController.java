package com.jf.logistics.controller;

import com.jf.logistics.model.dto.LoginDto;
import com.jf.logistics.model.entity.Admin;
import com.jf.logistics.model.entity.User;
import com.jf.logistics.service.AdminService;
import com.jf.logistics.service.UserService;
import com.jf.logistics.utils.JwtTokenUtil;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/logistics/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    public Admin save(@RequestBody Admin admin) throws Exception {
        return adminService.save(admin);
    }

    @DeleteMapping("")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    public void delete(String id){
        adminService.deleteAdmin(id);
    }

    @PostMapping("/selectAdminList")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    public Map<String, Object> selectAdminList(String companyName, String username, int page, int pageSize){
        return adminService.findAllByUsernameLikeOrCompanyNameLikeAndPage(companyName,username,page,pageSize);
    }

    @PostMapping("/login")
    public Map<String, Object> login(String type, @RequestBody LoginDto loginDto) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        Admin admin=type.equals("email") ? adminService.loginByEmail(loginDto) :adminService.loginByPassword(loginDto);
        User user=type.equals("email") ? userService.loginByEmail(loginDto) :userService.loginByPassword(loginDto);
        String token=null;
        if(admin!=null){
            token=adminService.createToken(admin,loginDto.isRemember() ? JwtTokenUtil.REMEMBER_EXPIRATION_TIME : JwtTokenUtil.EXPIRATION_TIME);
        }else if (user !=null){
            token=userService.createToken(user,loginDto.isRemember() ? JwtTokenUtil.REMEMBER_EXPIRATION_TIME : JwtTokenUtil.EXPIRATION_TIME);
        }else{
            token=userService.createToken(null,loginDto.isRemember() ? JwtTokenUtil.REMEMBER_EXPIRATION_TIME : JwtTokenUtil.EXPIRATION_TIME);
        }
        map.put("user",admin==null?user:admin);
        map.put("token",token);
        return map;
    }

    @PostMapping("/updatePwd")
    public void updatePwd(String password, String newPassword, HttpServletRequest request) throws Exception {
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        //登录的用户
        Map<String, Object> map= (Map<String, Object>) JwtTokenUtil.getUser(token);

        adminService.updatePwd(password,newPassword, (String) map.get("id"));
    }
}
