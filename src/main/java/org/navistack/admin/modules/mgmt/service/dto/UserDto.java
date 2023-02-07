package org.navistack.admin.modules.mgmt.service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.navistack.admin.modules.common.enums.Gender;
import org.navistack.admin.modules.common.enums.UserStatus;
import org.navistack.admin.support.validation.groups.Create;
import org.navistack.admin.support.validation.groups.Modify;

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
    @Pattern(regexp = "^\\d{1,17}$")
    private String mobileNumber;

    @NotEmpty
    @Email
    private String emailAddress;

    private String password;

    private UserStatus status;

    private Long orgId;

    private List<Long> roleIds;
}
