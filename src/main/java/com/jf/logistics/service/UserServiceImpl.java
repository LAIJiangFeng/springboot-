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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private AdminDao adminDao;
    @Override
    public User save(User user) throws Exception {
        boolean b = userDao.existsUserByUsername(user.getUsername());
        if(b&&user.getId()==null)  throw new Exception("此用户已存在");
        if(user.getUsername().length()<6 && user.getPassword().length()<6) throw new Exception("参数格式不正确");
        user.setCreateAt(DateUtil.getNowTimeString());
        //加密处理
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(user.getPassword());
        user.setPassword(encode);
        return userDao.save(user);
    }

    @Override
    public void deleteUser(String id) {
        userDao.deleteById(id);
    }

    @Override
    public Map<String,Object> findAllByUsernameLikeAndTypeAndPage(String name,String username, String type, String companyCode, String companyName, int page, int pageSize) {
        Pageable pageable= PageRequest.of(page-1,pageSize);
        Page<User> data = userDao.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                ArrayList<Predicate> list = new ArrayList<>();
                if(name!=null){
                    Predicate p1 = cb.like(root.get("name"), name+"%");
                    list.add(p1);
                }
                if(username!=null){
                    Predicate p1 = cb.like(root.get("username"), username+"%");
                    list.add(p1);
                }
               if(type!=null){
                   Predicate p1 = cb.equal(root.get("type"), type);
                   list.add(p1);
               }
               if(companyCode!=null){
                   Predicate p1 = cb.equal(root.get("companyCode"), companyCode);
                   list.add(p1);
               }
                if(companyName!=null){
                    Predicate p1 = cb.like(root.get("companyName"), companyName+"%");
                    list.add(p1);
                }

                Predicate[] p = new Predicate[list.size()];
                Predicate predicate = cb.and(list.toArray(p));
                return predicate;
            }
        }, pageable);
        HashMap<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("pageSize",pageSize);
        map.put("list",data.getContent());
        map.put("total",data.getTotalElements());
        return map;
    }

    @Override
    public String createToken(User user, long exp) {
        String rolesString = user.getRoles();
        String[] roles = rolesString != null ? rolesString.split(";") : null;
        return JwtTokenUtil.createToken(user.getUsername(),user,roles, exp);
    }

    @Override
    public User loginByPassword(LoginDto dto) throws Exception {
        User one = userDao.findUserByUsername(dto.getUsername());
        Admin admin = adminDao.findAdminByUsername(dto.getUsername());
        if (one==null&& admin==null) throw new Exception("不存在此用户");
        if(one!=null){
            //比较密码
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            boolean b = encoder.matches(dto.getPassword(), one.getPassword());
            if (!b) throw new Exception("用户名或密码错误");
        }
        return one;
    }

    @Override
    public User loginByEmail(LoginDto dto) throws Exception {
        return null;
    }

    @Override
    public List<User> findByCompanyCodeAndType(String companyCode, String type) {
        return userDao.findByCompanyCodeAndType(companyCode,type);
    }

}
