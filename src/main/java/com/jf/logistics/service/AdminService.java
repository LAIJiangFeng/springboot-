package com.jf.logistics.service;

import com.jf.logistics.model.dto.LoginDto;
import com.jf.logistics.model.entity.Admin;

import java.util.List;
import java.util.Map;

public interface AdminService {
    Admin save(Admin admin) throws Exception;

    void deleteAdmin(String id);

    void sendEmail(String email) throws Exception;

    Admin loginByPassword(LoginDto dto) throws Exception;

    Admin loginByEmail(LoginDto dto) throws Exception;

    //生成token
    String createToken(Admin admin, long exp);

    Map<String,Object> findAllByUsernameLikeOrCompanyNameLikeAndPage(String companyName, String username, int page, int pageSize);

    void updatePwd(String password, String newPassword,String id) throws Exception;
}
