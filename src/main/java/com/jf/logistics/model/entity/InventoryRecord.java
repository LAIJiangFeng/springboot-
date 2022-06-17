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
@Table(name = "Inventory_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class InventoryRecord {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2",strategy="org.hibernate.id.UUIDGenerator")
    private String id;

    //仓库id
    private String wid;

    //商品信息id
    private String cid;

    //商品名
    private String name;

    //数量
    private Integer count;

    //公司号
    private String companyCode;

    //公司名
    private String companyName;

    //入库时间
    private String updateTime;

    //出库-1;入库1
    private int type;

    //备注
    private String description;
}
