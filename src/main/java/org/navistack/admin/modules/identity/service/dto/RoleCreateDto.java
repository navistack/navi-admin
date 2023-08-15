package org.navistack.admin.modules.identity.service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class RoleCreateDto {
    @Pattern(regexp = "^[A-Za-z0-9$_]{1,48}$", message = "{validation.constraints.Role.code.message}")
    private String code;

    @NotEmpty
    @Size(min = 1, max = 80)
    private String name;

    @Size(max = 140)
    private String remarks;

    private List<Long> privilegeIds;
}
