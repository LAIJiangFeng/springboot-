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
@Table(name="company")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class Company {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2",strategy="org.hibernate.id.UUIDGenerator")
    private String id;
    private String companyName;
    private String companyCode;
    private String address;
    private String phone;
    private String createAt;
}
