package org.navistack.admin.modules.system.web.rest.vm;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
