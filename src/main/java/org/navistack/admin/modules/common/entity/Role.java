package org.navistack.admin.modules.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.navistack.framework.mybatisplusplus.entity.AuditingEntity;

@EqualsAndHashCode(callSuper = true)
@Data
public class Role extends AuditingEntity<Long> {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String code;

    private String name;

    private String remarks;
}
