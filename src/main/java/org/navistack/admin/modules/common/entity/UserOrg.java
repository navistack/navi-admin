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
public class UserOrg extends AuditingEntity<Long> {
    private Long id;

    private Long userId;

    private Long orgId;

    public UserOrg(Long userId, Long orgId) {
        this.userId = userId;
        this.orgId = orgId;
    }

    public static UserOrg of(Long userId, Long orgId) {
        return new UserOrg(userId, orgId);
    }
}
