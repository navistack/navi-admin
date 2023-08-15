package org.navistack.admin.modules.identity.service.vm;

import lombok.Data;

@Data
public class RoleVm {
    private Long id;

    private String code;

    private String name;

    private String remarks;
}
