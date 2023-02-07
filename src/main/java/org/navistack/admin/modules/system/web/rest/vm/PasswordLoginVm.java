package org.navistack.admin.modules.system.web.rest.vm;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordLoginVm {
    @NotEmpty
    @Size(min = 3, max = 24)
    @Pattern(regexp = "^[A-Za-z0-9_.-]+$")
    private String loginName;

    @NotEmpty
    @Size(min = 1)
    private String password;
}
