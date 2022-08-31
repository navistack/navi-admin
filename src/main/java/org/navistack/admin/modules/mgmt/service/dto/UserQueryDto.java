package org.navistack.admin.modules.mgmt.service.dto;

import lombok.Data;
import org.navistack.admin.modules.common.enums.UserStatus;

@Data
public class UserQueryDto {
    private String loginName;

    private String mobileNumber;

    private String emailAddress;

    private UserStatus status;
}
