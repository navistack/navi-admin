package org.navistack.admin.modules.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.navistack.framework.batis.entity.AuditingEntity;

/**
 * @see <a href="https://en.wikipedia.org/wiki/ISO_3166-2">ISO3166-2</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Region extends AuditingEntity<Long> {
    private Long id;

    private String code;

    private String name;

    private String parentCode;

    private String remarks;
}
