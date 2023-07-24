package org.navistack.admin.modules.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.navistack.framework.batis.entity.IdentifyingEntity;

/**
 * @see <a href="https://en.wikipedia.org/wiki/ISO_3166-2">ISO3166-2</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Region extends IdentifyingEntity<Long> {
    private String code;

    private String name;

    private String parentCode;

    private String remarks;
}
