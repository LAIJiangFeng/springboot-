package com.jf.logistics.service;

import com.jf.logistics.dao.AdminDao;
import com.jf.logistics.dao.UserDao;
import com.jf.logistics.model.dto.LoginDto;
import com.jf.logistics.model.entity.Admin;
import com.jf.logistics.model.entity.User;
import com.jf.logistics.utils.DateUtil;
import com.jf.logistics.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    @Autowired
    private UserDao userDao;

    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public Admin save(Admin admin) throws Exception {
        boolean b = adminDao.existsAdminByUsername(admin.getUsername());
        if(b&&admin.getId()==null)  throw new Exception("此用户已存在");
        if(admin.getUsername().length()<6 && admin.getPassword().length()<6) throw new Exception("参数格式不正确");
        admin.setCreateAt(DateUtil.getNowTimeString());
        //加密处理
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(admin.getPassword());
        admin.setPassword(encode);
        return adminDao.save(admin);
    }

    @Override
    public void deleteAdmin(String id) {
        adminDao.deleteById(id);
    }

    @Override
    public void sendEmail(String email) throws Exception {

    }

    @Override
    public Admin loginByPassword(LoginDto dto) throws Exception {
        Admin one = adminDao.findAdminByUsername(dto.getUsername());
        User user = userDao.findUserByUsername(dto.getUsername());
        if (one==null&&user==null) throw new Exception("不存在此用户");
        if(one!=null){
            //比较密码
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            boolean b = encoder.matches(dto.getPassword(), one.getPassword());
            if (!b) throw new Exception("用户名或密码错误");
        }

        // 用户验证
//        Authentication authentication = null;
//        try
//        {
//            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
//            authentication = authenticationManager
//                    .authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
//        }
//        catch (Exception e)
//        {
//            if (e instanceof BadCredentialsException)
//            {
//
//                throw new Exception("用户名或密码错误");
//            }
//            else
//            {
//                throw new Exception("错误");
//            }
//        }
//        Admin one = (Admin) authentication.getPrincipal();
//        Admin admin = new Admin();
        return one;
    }

    @Override
    public Admin loginByEmail(LoginDto dto) throws Exception {
        return null;
    }

    @Override
    public String createToken(Admin admin, long exp) {
        String rolesString = admin.getRoles();
        String[] roles = rolesString != null ? rolesString.split(";") : null;
        return JwtTokenUtil.createToken(admin.getUsername(),admin, roles, exp);
    }

    @Override
    public Map<String,Object> findAllByUsernameLikeOrCompanyNameLikeAndPage(String companyName, String username, int page, int pageSize) {

        Pageable pageable= PageRequest.of(page-1,pageSize);
        Page<Admin> data = adminDao.findAll(new Specification<Admin>() {
            @Override
            public Predicate toPredicate(Root<Admin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                ArrayList<Predicate> list = new ArrayList<>();
                if(companyName!=null){
                    Predicate p1 = cb.like(root.get("companyName"), companyName+"%");
                    list.add(p1);
                }
                if(username!=null){
                    Predicate p2 = cb.like(root.get("username"), username+"%");
                    list.add(p2);
                }
                Predicate[] p = new Predicate[list.size()];
                Predicate predicate = cb.and(list.toArray(p));
                return predicate;
            }
        }, pageable);
//        System.out.println(data.getTotalElements());//总条数
//        System.out.println(data.getTotalPages());//总页数
//        System.out.println(data.get());
//        System.out.println(data.getContent());//数据列表集合
        HashMap<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("pageSize",pageSize);
        map.put("list",data.getContent());
        map.put("total",data.getTotalElements());
        return map;
    }

    @Override
    public void updatePwd(String password, String newPassword, String id) throws Exception {
        Optional<Admin> a = adminDao.findById(id);
        Optional<User> u = userDao.findById(id);
        if(a.isPresent()){
            Admin admin = a.get();

            //比较密码
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            boolean b = encoder.matches(password, admin.getPassword());
            if (!b) throw new Exception("原密码不正确!");

            //加密处理
            String encode = encoder.encode(newPassword);
            admin.setPassword(encode);
            adminDao.save(admin);
        }else if(u.isPresent()){
            User user = u.get();

            //比较密码
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            boolean b = encoder.matches(password, user.getPassword());
            if (!b) throw new Exception("原密码不正确!");

            //加密处理
            String encode = encoder.encode(newPassword);
            user.setPassword(encode);
            userDao.save(user);
        }else {
            throw new Exception("修改异常,查询不到用户");
        }
    }
}
