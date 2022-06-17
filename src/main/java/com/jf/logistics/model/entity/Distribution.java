package com.jf.logistics.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "distribution")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Distribution {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    //配送地址
    private String address;

    //注意
    private String care;

    //驾驶人id
    private String did;

    //驾驶人
    private String driver;

    //车牌号
    private String number;

    //客户电话
    private String phone;

    //配送状态
    private Integer status;

    //预计送达时间
    private String time;

    //是否加急
    private boolean urgent;

    //车id
    private String vid;

    //公司名
    private String companyName;

    //公司号
    private String companyCode;

    //当前位置
    private String location;

    //用户id
    private String uid;

    //备注
    private String description;

    //商品id
    private String cid;

    //商品数量
    private int count;

    //商品名称
    private String cname;

}
