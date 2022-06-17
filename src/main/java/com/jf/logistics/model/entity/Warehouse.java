package com.jf.logistics.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="warehouse")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Warehouse {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2",strategy="org.hibernate.id.UUIDGenerator")
    private String id;

    private String name;

    private String principle;

    private String phone;

    private String updateAt;

    private String companyCode;

    private String companyName;
}
