package org.navistack.admin.modules.identity.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.navistack.framework.batis.entity.IdentifyingEntity;

@EqualsAndHashCode(callSuper = true)
@Data
public class Organization extends IdentifyingEntity<Long> {
    private String code;

    private String name;

    private Long superId;

    private String remarks;
}
