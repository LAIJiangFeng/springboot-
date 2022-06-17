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
@Table(name = "commodity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class Commodity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2",strategy="org.hibernate.id.UUIDGenerator")
    private String id;
    private int count;
    private String description;
    private String name;
    private double price;
    private String updateAt;
    private String companyCode;
    private String companyName;
    private int status;//是否入库
    private String userId; //用户id
}
