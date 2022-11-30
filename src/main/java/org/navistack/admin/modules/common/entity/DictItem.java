package org.navistack.admin.modules.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.navistack.framework.batis.entity.AuditingEntity;

@EqualsAndHashCode(callSuper = true)
@Data
public class DictItem extends AuditingEntity<Long> {
    private Long id;

    private String name;

    private String itKey;

    private String itValue;

    private String remarks;

    private String dictCode;
}
