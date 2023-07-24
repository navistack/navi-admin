package org.navistack.admin.modules.identity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.navistack.framework.batis.entity.IdentifyingEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class UserOrganization extends IdentifyingEntity<Long> {
    private Long userId;

    private Long organizationId;
}
