package com.jf.logistics.service;

import com.jf.logistics.dao.AdminDao;
import com.jf.logistics.dao.UserDao;
import com.jf.logistics.model.dto.LoginUser;
import com.jf.logistics.model.entity.Admin;
import com.jf.logistics.model.entity.User;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户验证处理
 *
 * @author ruoyi
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {

        Admin one = adminDao.findAdminByUsername(username);
        User user = userDao.findUserByUsername(username);
        if (one==null&&user==null)
        {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException("登录用户：" + username + " 不存在");
        }
        String uname=null;
        String pwd=null;
        if(one!=null){
            uname=one.getUsername();
            pwd=one.getPassword();
        }

        if(user!=null){
            uname=user.getUsername();
            pwd=user.getPassword();
        }
        return createLoginUser(uname,pwd);
    }

    public UserDetails createLoginUser(String username,String password)
    {
        return new LoginUser(username,password);
    }
}
