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

/**
 * 1.实体类和表的映射关系
 * @Entity 生明实体类
 * @Table   表和实体类的映射关系
 * 2.类中属性和表中字段的映射关系
 * @Id     主键id
 * @GeneratedValue 主键的生成策略
 * @Column 属性和表中字段的映射关系
 */
@Entity
@Table(name="admin")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class Admin {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2",strategy="org.hibernate.id.UUIDGenerator")
    private String id;
    private String createAt;
    private String username;
    private String password;
    private String roles;
    private String companyCode;
    private String companyName;
}
