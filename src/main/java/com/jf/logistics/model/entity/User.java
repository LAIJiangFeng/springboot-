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
@Table(name="user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class User{
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
    private String type="user";
    private int status;//是否不空闲
    private String name; //名称
}
