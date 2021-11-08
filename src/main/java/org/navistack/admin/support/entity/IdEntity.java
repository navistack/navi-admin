package org.navistack.admin.support.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class IdEntity<T> extends AuditingEntity<T> {
    private T id;
}
