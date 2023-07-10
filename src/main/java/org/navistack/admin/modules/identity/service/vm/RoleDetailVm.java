package org.navistack.admin.modules.identity.service.vm;

import lombok.Data;

import java.util.List;

@Data
public class RoleDetailVm {
    private Long id;

    private String code;

    private String name;

    private String remarks;

    private List<Long> privilegeIds;
}
