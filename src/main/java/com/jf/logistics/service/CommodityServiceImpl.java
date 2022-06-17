package com.jf.logistics.service;

import com.jf.logistics.dao.CommodityDao;
import com.jf.logistics.model.entity.Commodity;
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
import java.util.*;

@Service
public class CommodityServiceImpl implements CommodityService {
    @Autowired
    private CommodityDao commodityDao;
    @Override
    public Commodity save(Commodity commodity) {
        commodity.setUpdateAt(DateUtil.getNowTimeString());
        return commodityDao.save(commodity);
    }

    @Override
    public Map<String, Object> findCommodityByNameLikeAndPriceAndPage(String name, Double price, String companyCode, String companyName,String userId, int page, int pageSize) {
       Pageable pageable= PageRequest.of(page-1,pageSize);
       Page<Commodity> data= commodityDao.findAll(new Specification<Commodity>() {
            @Override
            public Predicate toPredicate(Root<Commodity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                ArrayList<Predicate> list = new ArrayList<>();
                if(name!=null){
                    Predicate p = cb.like(root.get("name"), name + "%");
                    list.add(p);
                }
                if(price!=null){
                    Predicate p = cb.equal(root.get("price"), price );
                    list.add(p);
                }
                if(companyCode!=null){
                    Predicate p = cb.equal(root.get("companyCode"), companyCode );
                    list.add(p);
                }
                if(companyName!=null){
                    Predicate p = cb.like(root.get("companyName"), companyName+ "%");
                    list.add(p);
                }

                if(userId!=null){
                    Predicate p = cb.equal(root.get("userId"), userId);
                    list.add(p);
                }
                Predicate[] p = new Predicate[list.size()];
                Predicate predicate = cb.and(list.toArray(p));
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
    public void deleteCommodity(String id) {
        commodityDao.deleteById(id);
    }

    @Override
    public Optional<Commodity> findById(String id) {
        return commodityDao.findById(id);
    }

    @Override
    public List<Commodity> findByCompanyCode(String companyCode) {
        return commodityDao.findByCompanyCode(companyCode);
    }

    @Override
    public List<Commodity> findAll() {
        return commodityDao.findAll();
    }
}
