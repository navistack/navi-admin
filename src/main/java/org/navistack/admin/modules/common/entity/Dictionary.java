package org.navistack.admin.modules.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.navistack.framework.batis.entity.IdentifyingEntity;

@EqualsAndHashCode(callSuper = true)
@Data
public class Dictionary extends IdentifyingEntity<Long> {
    private String code;

    private String name;

    private String remarks;
}
