package org.navistack.admin.modules.common.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrganizationQuery {
    private Long id;

    private Long userId;

    private Long organizationId;
}