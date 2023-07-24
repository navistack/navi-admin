package org.navistack.admin.modules.identity.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.navistack.framework.batis.entity.IdentifyingEntity;

@EqualsAndHashCode(callSuper = true)
@Data
public class Privilege extends IdentifyingEntity<Long> {
    private String code;

    private String name;

    private Long parentId;

    private String remarks;
}
