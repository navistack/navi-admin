package org.navistack.admin.modules.identity.service.vm;

import lombok.Data;

@Data
public class PrivilegeVm {
    private Long id;

    private String code;

    private String name;

    private Long parentId;

    private String remarks;
}
