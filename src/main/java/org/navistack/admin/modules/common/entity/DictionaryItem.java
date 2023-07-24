package org.navistack.admin.modules.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.navistack.framework.batis.entity.IdentifyingEntity;

@EqualsAndHashCode(callSuper = true)
@Data
public class DictionaryItem extends IdentifyingEntity<Long> {
    private String code;

    private String name;

    private Long dictionaryId;

    private String remarks;
}
