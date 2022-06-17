package com.jf.logistics.service;

import com.jf.logistics.dao.CompanyDao;
import com.jf.logistics.model.entity.Company;
import com.jf.logistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyDao companyDao;
    @Override
    public Company save(Company company) throws Exception{
        boolean b = companyDao.existsCompanyByCompanyCode(company.getCompanyCode());
        if(b&&company.getId()==null) throw new Exception("该公司已存在");
        company.setCreateAt(DateUtil.getNowTimeString());
        return companyDao.save(company);
    }

    @Override
    public Map<String, Object> findCompanyByCompanyNameLike(String companyName, int page, int pageSize) {
        Pageable pageable= PageRequest.of(page - 1, pageSize);
        Page<Company> data=companyDao.findAll(new Specification<Company>() {
            @Override
            public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate=null;
                if(companyName!=null) {
                     predicate = cb.like(root.get("companyName"), companyName + "%");
                }
                return predicate;
            }
        },pageable);
        HashMap<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("pageSize",pageSize);
        map.put("list",data.getContent());
        map.put("total",data.getTotalElements());
        return map;
    }

    @Override
    public void delete(String id) {
        companyDao.deleteById(id);
    }

    @Override
    public List<Company> findAll() {
        return companyDao.findAll();
    }
}
