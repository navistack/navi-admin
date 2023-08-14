package org.navistack.admin.support.mybatis;

import lombok.Data;
import org.navistack.framework.data.AuditingProperties;

import java.time.Instant;

@Data
public class AuditingDataObject<T> implements AuditingProperties<T> {
    private Instant createdAt;
    private T createdBy;
    private Instant updatedAt;
    private T updatedBy;
    private boolean deleted;
    private Instant deletedAt;
    private T deletedBy;
}
