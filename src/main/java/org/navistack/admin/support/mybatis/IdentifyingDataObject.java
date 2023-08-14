package org.navistack.admin.support.mybatis;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.navistack.framework.data.IdentifyingProperties;

@EqualsAndHashCode(callSuper = true)
@Data
public class IdentifyingDataObject<T> extends AuditingDataObject<T> implements IdentifyingProperties<T> {
    private T id;
}
