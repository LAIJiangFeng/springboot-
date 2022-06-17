package com.jf.logistics.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Role {

    //超级管理员 不对外提供添加此权限的方法
    ROLE_SUPER_ADMIN("ROLE_SUPER_ADMIN", "超级管理员"),

    ROLE_ADMIN("ROLE_ADMIN", "管理员相关权限"),

    ROLE_COMMODITY("ROLE_COMMODITY", "商品相关权限"),

    ROLE_EMPLOYEE("ROLE_EMPLOYEE", "员工相关权限"),

    ROLE_SALE("ROLE_SALE", "销售相关权限"),

    ROLE_WAREHOUSE("ROLE_WAREHOUSE", "仓库相关权限"),

    ROLE_USER("ROLE_USER", "用户相关权限"),

    ROLE_LOCATION("ROLE_LOCATION","定位相关权限"),

    ROLE_DISTRIBUTION("ROLE_DISTRIBUTION","配送相关权限");

    private final String value;

    private final String description;

    public static final Role[] ROLES = {
            ROLE_ADMIN,
            ROLE_COMMODITY,
            ROLE_EMPLOYEE,
            ROLE_SALE,
            ROLE_WAREHOUSE,
            ROLE_USER,
            ROLE_LOCATION,
            ROLE_DISTRIBUTION
    };

    Role(String value, String description) {
        this.value = value;
        this.description = description;
    }

}
