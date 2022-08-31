package org.navistack.admin.modules.mgmt.service.dto;

import lombok.Data;

@Data
public class OrgQueryDto {
    private String code;

    private String name;

    private Long superId;
}
