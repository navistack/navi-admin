package org.navistack.admin.modules.identity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.navistack.framework.batis.entity.AuditingEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrganization extends AuditingEntity<Long> {
    private Long id;

    private Long userId;

    private Long organizationId;

    public UserOrganization(Long userId, Long organizationId) {
        this.userId = userId;
        this.organizationId = organizationId;
    }

    public static UserOrganization of(Long userId, Long organizationId) {
        return new UserOrganization(userId, organizationId);
    }
}
