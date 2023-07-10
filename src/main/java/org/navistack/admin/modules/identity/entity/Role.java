package org.navistack.admin.modules.identity.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.navistack.framework.batis.entity.AuditingEntity;

@EqualsAndHashCode(callSuper = true)
@Data
public class Role extends AuditingEntity<Long> {
    private Long id;

    private String code;

    private String name;

    private String remarks;
}
