package org.navistack.admin.modules.mgmt.service.dto;

import lombok.Data;

@Data
public class GeoDivisionQueryParams {
    private Long id;

    private String code;

    private String name;

    private String parentCode;
}
