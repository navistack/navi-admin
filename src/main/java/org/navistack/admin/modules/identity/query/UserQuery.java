package org.navistack.admin.modules.identity.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.navistack.admin.modules.identity.enums.UserStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserQuery {
    private Long id;

    private String nickName;

    private String loginName;

    private String mobileNumber;

    private String emailAddress;

    private UserStatus status;
}
