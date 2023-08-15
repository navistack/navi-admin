package org.navistack.admin.modules.identity.service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PrivilegeModifyDto {
    @NotNull
    private Long id;

    @NotEmpty
    @Pattern(regexp = "^[A-Za-z0-9:]{1,48}$", message = "{validation.constraints.Privilege.code.message}")
    private String code;

    @NotEmpty
    @Size(min = 1, max = 80)
    private String name;

    private Long parentId;

    @Size(max = 140)
    private String remarks;
}
