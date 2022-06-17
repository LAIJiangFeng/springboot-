package com.jf.logistics.service;

import com.jf.logistics.dao.SaleDao;
import com.jf.logistics.model.entity.Commodity;
import com.jf.logistics.model.entity.Sale;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleDao saleDao;

    @Autowired
    private CommodityService commodityService;

    @Override
    public Sale save(Sale sale) {
        sale.setCreateAt(DateUtil.getNowTimeString());
        //找到商品
        Optional<Commodity> c = commodityService.findById(sale.getCid());
        Commodity commodity = c.get();
        //总价=单价*数量
        double price = commodity.getPrice() * sale.getCount();
        sale.setPrice(price);

        return saleDao.save(sale);
    }

    @Override
    public Map<String, Object> findByCnameAndCompanyNameAndPage(String cname, String companyName,String companyCode,String uid,int page, int pageSize) {
        Pageable pageable=PageRequest.of(page-1,pageSize);
        Page<Sale> data=saleDao.findAll(new Specification<Sale>() {
            @Override
            public Predicate toPredicate(Root<Sale> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                ArrayList<Predicate> list = new ArrayList<>();
                if(cname!=null){
                    Predicate p = cb.like(root.get("cname"), cname + '%');
                    list.add(p);
                }

                if(companyName!=null){
                    Predicate p = cb.like(root.get("companyName"), companyName + '%');
                    list.add(p);
                }

                if(companyCode!=null){
                    Predicate p = cb.equal(root.get("companyCode"), companyCode);
                    list.add(p);
                }

                if(uid!=null){
                    Predicate p = cb.equal(root.get("uid"), uid);
                    list.add(p);
                }
                Predicate[] p=new Predicate[list.size()];
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
    public void delete(String id) {
        saleDao.deleteById(id);
    }
}
