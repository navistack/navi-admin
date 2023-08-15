package org.navistack.admin.modules.common.service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegionModifyDto {
    protected static final String CODE_PATTERN = "^[A-Za-z0-9-]{1,48}$";
    protected static final String CODE_VALIDATION_MESSAGE = "{validation.constraints.Region.code.message}";

    @NotNull
    private Long id;

    @NotEmpty
    @Pattern(regexp = CODE_PATTERN, message = CODE_VALIDATION_MESSAGE)
    private String code;

    @NotEmpty
    @Size(min = 1, max = 80)
    private String name;

    @NotEmpty
    @Pattern(regexp = CODE_PATTERN, message = CODE_VALIDATION_MESSAGE)
    private String parentCode;

    @Size(max = 140)
    private String remarks;
}
