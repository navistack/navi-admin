package org.navistack.admin.modules.identity.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationQuery {
    private Long id;

    private String code;

    private String name;

    private Long superId;
}
