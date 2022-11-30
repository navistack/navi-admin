package org.navistack.admin.modules.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.navistack.framework.batis.entity.AuditingEntity;

@EqualsAndHashCode(callSuper = true)
@Data
public class Org extends AuditingEntity<Long> {
    private Long id;

    private String code;

    private String name;

    private Long superId;

    private String remarks;
}
