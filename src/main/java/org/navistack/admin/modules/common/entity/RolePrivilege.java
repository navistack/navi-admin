package org.navistack.admin.modules.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.navistack.admin.support.entity.AuditingEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePrivilege extends AuditingEntity<Long> {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long roleId;

    private Long privilegeId;

    public RolePrivilege(Long roleId, Long privilegeId) {
        this.roleId = roleId;
        this.privilegeId = privilegeId;
    }

    public static RolePrivilege of(Long roleId, Long privilegeId) {
        return new RolePrivilege(roleId, privilegeId);
    }
}
