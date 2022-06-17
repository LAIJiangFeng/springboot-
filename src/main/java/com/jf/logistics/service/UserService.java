package com.jf.logistics.service;

import com.jf.logistics.model.dto.LoginDto;
import com.jf.logistics.model.entity.User;


import java.util.List;
import java.util.Map;

public interface UserService {
    User save(User user) throws Exception;

    void deleteUser(String id);

    Map<String,Object> findAllByUsernameLikeAndTypeAndPage(String name,String username, String type, String companyCode, String companyName, int page, int pageSize);

    //生成token
    String createToken(User user, long exp);


    User loginByPassword(LoginDto dto) throws Exception;

    User loginByEmail(LoginDto dto) throws Exception;

    List<User> findByCompanyCodeAndType(String companyCode,String type);
}
