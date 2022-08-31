package org.navistack.admin.modules.mgmt.service.dto;

import lombok.Data;

@Data
public class PrivilegeQueryDto {
    private Long id;

    private String code;

    private String name;

    private Long parentId;
}
