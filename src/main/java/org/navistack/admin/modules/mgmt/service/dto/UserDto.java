package org.navistack.admin.modules.mgmt.service.dto;

import lombok.Data;
import org.navistack.admin.modules.common.enums.Gender;
import org.navistack.admin.modules.common.enums.UserStatus;
import org.navistack.framework.crudsupport.validation.groups.Create;
import org.navistack.framework.crudsupport.validation.groups.Modify;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
public class UserDto {
    @Null(groups = Create.class)
    @NotNull(groups = Modify.class)
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 12)
    private String nickName;

    @Size(max = 240)
    private String avatarUrl;

    private Gender gender;

    private LocalDate birthday;

    @Size(min = 3, max = 24)
    @Pattern(regexp = "^[A-Za-z0-9_.-]+$")
    private String loginName;

    @NotEmpty
    @Pattern(regexp = "^\\d{1,3}$")
    private String mobilePrefix;

    @NotEmpty
    @Pattern(regexp = "^\\d{1,14}$")
    private String mobileNumber;

    @NotEmpty
    @Email
    private String emailAddress;

    private String password;

    private UserStatus status;

    private List<Long> roleIds;
}
