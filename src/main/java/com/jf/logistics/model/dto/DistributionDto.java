package com.jf.logistics.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Accessors(chain = true)
public class DistributionDto {

    private String id;

    //配送地址
    private String address;

    //驾驶人id
    private String did;

    //驾驶人
    private String driver;

    //车牌号
    private String number;

    //配送状态
    private Integer status;

    //是否加急
    private boolean urgent;

    //车id
    private String vid;

    //公司名
    private String companyName;

    //公司号
    private String companyCode;

    //用户id
    private String uid;


    //商品id
    private String cid;

    //商品名称
    private String cname;

    //页数
    private int page;

    //一页多少行
    private int pageSize;


}
