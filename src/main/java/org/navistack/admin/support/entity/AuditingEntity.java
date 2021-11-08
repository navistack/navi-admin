package org.navistack.admin.support.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.navistack.framework.data.AuditingProperties;

import java.time.Instant;

@Data
public class AuditingEntity<T> implements AuditingProperties<T> {
    @TableField(fill = FieldFill.INSERT)
    private Instant createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Instant updatedAt;

    private Instant deletedAt;

    @TableField(fill = FieldFill.INSERT)
    private T createdBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private T updatedBy;

    private T deletedBy;
}
