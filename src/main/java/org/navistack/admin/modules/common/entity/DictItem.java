package org.navistack.admin.modules.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.navistack.admin.support.entity.AuditingEntity;

@EqualsAndHashCode(callSuper = true)
@Data
public class DictItem extends AuditingEntity<Long> {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String itKey;

    private String itValue;

    private String description;

    private String dictCode;
}
