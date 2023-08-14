package org.navistack.admin.modules.identity.dtobj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.navistack.admin.support.mybatis.IdentifyingDataObject;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class UserOrganizationDo extends IdentifyingDataObject<Long> {
    private Long userId;

    private Long organizationId;
}
