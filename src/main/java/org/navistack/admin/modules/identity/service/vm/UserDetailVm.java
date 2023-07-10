package org.navistack.admin.modules.identity.service.vm;

import lombok.Data;
import org.navistack.admin.modules.identity.enums.Gender;
import org.navistack.admin.modules.identity.enums.UserStatus;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserDetailVm {
    private Long id;

    private String nickName;

    private String avatarUrl;

    private Gender gender;

    private LocalDate birthday;

    private String loginName;

    private String mobileNumber;

    private String emailAddress;

    private String password;

    private UserStatus status;

    private List<Long> roleIds;
}
