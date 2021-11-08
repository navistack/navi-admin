package org.navistack.admin.modules.mgmt.service.dto;

import lombok.Data;

@Data
public class PrivilegeQueryParams {
    private Long id;

    private String code;

    private String name;

    private Long parentId;
}
