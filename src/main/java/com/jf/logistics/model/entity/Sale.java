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
@Table(name = "sale")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Sale {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2",strategy="org.hibernate.id.UUIDGenerator")
    private String id;

    //商品名
    private String cname;

    //商品id
    private String cid;

    //销售用户
    private String saleUser;

    //销售用户id
    private String uid;

    //公司名
    private String companyName;

    //公司号
    private String companyCode;

    //数量
    private int count;

    //创建时间
    private String createAt;

    //描述
    private String description;

    //车牌号
    private String number;

    //是否付款
    private boolean pay;

    //预留电话
    private String phone;

    //总价
    private double price;
}
