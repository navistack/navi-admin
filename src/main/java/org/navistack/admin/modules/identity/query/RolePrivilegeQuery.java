package org.navistack.admin.modules.identity.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolePrivilegeQuery {
    private Long id;

    private Long roleId;

    private Long privilegeId;
}
