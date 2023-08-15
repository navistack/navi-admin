package org.navistack.admin.modules.identity.service.vm;

import lombok.Data;

@Data
public class OrganizationVm {
    private Long id;

    private String code;

    private String name;

    private Long superId;

    private String remarks;
}
