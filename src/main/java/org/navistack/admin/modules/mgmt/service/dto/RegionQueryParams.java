package org.navistack.admin.modules.mgmt.service.dto;

import lombok.Data;

@Data
public class RegionQueryParams {
    private Long id;

    private String code;

    private String name;

    private String parentCode;
}
