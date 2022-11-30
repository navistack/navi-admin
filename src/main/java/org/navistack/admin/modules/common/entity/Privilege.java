package org.navistack.admin.modules.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.navistack.framework.batis.entity.AuditingEntity;

@EqualsAndHashCode(callSuper = true)
@Data
public class Privilege extends AuditingEntity<Long> {
    private Long id;

    private String code;

    private String name;

    private Long parentId;

    private String remarks;
}
