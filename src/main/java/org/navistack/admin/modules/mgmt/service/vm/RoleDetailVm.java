package org.navistack.admin.modules.mgmt.service.vm;

import lombok.Data;

import java.util.List;

@Data
public class RoleDetailVm {
    private Long id;

    private String code;

    private String name;

    private String description;

    private List<Long> privilegeIds;
}
