package org.navistack.admin.modules.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.navistack.framework.batis.entity.AuditingEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole extends AuditingEntity<Long> {
    private Long id;

    private Long userId;

    private Long roleId;

    public UserRole(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public static UserRole of(Long userId, Long roleId) {
        return new UserRole(userId, roleId);
    }
}
