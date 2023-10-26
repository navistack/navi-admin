package org.navistack.admin.modules.identity.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.navistack.admin.modules.identity.enums.Gender;
import org.navistack.admin.modules.identity.enums.UserStatus;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserModifyDto {
    @NotNull
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

    private List<Long> roleIds;
}
