package com.jf.logistics.service;

import com.alibaba.fastjson.JSONObject;
import com.jf.logistics.dao.*;
import com.jf.logistics.model.dto.DistributionDto;
import com.jf.logistics.model.entity.Commodity;
import com.jf.logistics.model.entity.Distribution;
import com.jf.logistics.model.entity.User;
import com.jf.logistics.model.entity.Vehicle;
import com.jf.logistics.utils.HttpRequest;
import org.apache.http.HttpException;
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
import java.io.IOException;
import java.util.*;

@Service
public class DistributionServiceImpl implements DistributionService {
    @Autowired
    private DistributionDao distributionDao;

    @Autowired
    private VehicleDao vehicleDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CommodityDao commodityDao;

    @Override
    public Distribution save(Distribution distribution) throws Exception {

        //申请配送
        if(distribution.getId()==null || "".equals(distribution.getId())){

            //判断库存是否够
            Optional<Commodity> c = commodityDao.findById(distribution.getCid());
            Commodity commodity = c.get();
            if(commodity.getCount()<distribution.getCount()) throw new Exception("商品库存不足,该商品剩余"+commodity.getCount());

            //改变车辆状态
            Optional<Vehicle> v = vehicleDao.findById(distribution.getVid());
            Vehicle vehicle = v.get();
            vehicle.setDriving(true);
            vehicleDao.save(vehicle);

            //改变驾驶人状态
            Optional<User> u = userDao.findById(distribution.getDid());
            User user = u.get();
            user.setStatus(1);
            userDao.save(user);
        }

        //送达
        if(distribution.getStatus()==2){

            //改变车辆状态
            Optional<Vehicle> v = vehicleDao.findById(distribution.getVid());
            Vehicle vehicle = v.get();
            vehicle.setDriving(false);
            vehicleDao.save(vehicle);

            //改变驾驶人状态
            Optional<User> u = userDao.findById(distribution.getDid());
            User user = u.get();
            user.setStatus(0);
            userDao.save(user);
        }


        return distributionDao.save(distribution);
    }

    @Override
    public Map<String, Object> findByDistributionDto(DistributionDto distributionDto) {
        Pageable pageable= PageRequest.of(distributionDto.getPage()-1,distributionDto.getPageSize());
        Page<Distribution> data= distributionDao.findAll(new Specification<Distribution>() {
            @Override
            public Predicate toPredicate(Root<Distribution> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                ArrayList<Predicate> list = new ArrayList<>();
                if(distributionDto.getCompanyCode()!=null){
                    Predicate p = cb.equal(root.get("companyCode"), distributionDto.getCompanyCode());
                    list.add(p);
                }
                if(distributionDto.getCompanyName()!=null){
                    Predicate p = cb.like(root.get("companyName"), distributionDto.getCompanyName()+"%" );
                    list.add(p);
                }
                if(distributionDto.getCname()!=null){
                    Predicate p = cb.like(root.get("cname"), distributionDto.getCname()+"%" );
                    list.add(p);
                }

                if(distributionDto.getUid()!=null){
                    Predicate p = cb.equal(root.get("uid"), distributionDto.getUid());
                    list.add(p);
                }

                if(distributionDto.getDid()!=null){
                    Predicate p = cb.equal(root.get("did"), distributionDto.getDid());
                    list.add(p);
                }

                if(distributionDto.getStatus()!=null){
                    Predicate p = cb.equal(root.get("status"), distributionDto.getStatus());
                    list.add(p);
                }

                Predicate[] p = new Predicate[list.size()];
                Predicate predicate = cb.and(list.toArray(p));
                return predicate;
            }
        },pageable);
        HashMap<String, Object> map = new HashMap<>();
        map.put("page",distributionDto.getPage());
        map.put("pageSize",distributionDto.getPageSize());
        map.put("list",data.getContent());
        map.put("total",data.getTotalElements());
        return map;
    }

    @Override
    public void delete(String id) {
        Optional<Distribution> d = distributionDao.findById(id);
        Distribution distribution = d.get();

        //改变车辆状态
        Optional<Vehicle> v = vehicleDao.findById(distribution.getVid());
        Vehicle vehicle = v.get();
        vehicle.setDriving(false);
        vehicleDao.save(vehicle);

        //改变驾驶人状态
        Optional<User> u = userDao.findById(distribution.getDid());
        User user = u.get();
        user.setStatus(0);
        userDao.save(user);

        distributionDao.deleteById(id);
    }

    @Override
    public Distribution findById(String id) {
        Optional<Distribution> d = distributionDao.findById(id);
        Distribution distribution = d.get();
        return distribution;
    }

    @Override
    public  Map<String,String> getLocation(Double latitude,Double longitude,String address) {
        Map<String,String> map = new HashMap<>();
        if(!"".equals(address)){
            map.put("location",address);
            return map;
        }

        if(latitude!=null && longitude!=null) {
            //地图服务的key
            String key = "L6ABZ-NXNE2-SCCUG-CRNTY-UELKV-2UBGA";
            //location=纬度,经度
            String url = "https://apis.map.qq.com/ws/geocoder/v1/?location=" + latitude + "," + longitude + "&key=" + key + "";
            try {
                String jsonStr = HttpRequest.sendGet(url);
                JSONObject jsonObject = JSONObject.parseObject(jsonStr);
                JSONObject  result= (JSONObject) jsonObject.get("result");
                address=(String) result.get("address");
            } catch (HttpException | IOException e) {
                e.printStackTrace();
            }
        }
        map.put("location",address);
        return map;
    }

    @Override
    public List<Distribution> findByCompanyCodeAndDid(String companyCode, String did) {
        return distributionDao.findByCompanyCodeAndDid(companyCode,did);
    }


}
