package org.navistack.admin.modules.common.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginNameQuery {
    private String loginName;

    private String mobileNumber;

    private String emailAddress;
}
