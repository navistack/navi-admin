package org.navistack.admin.modules.mgmt.service.dto;

import lombok.Data;
import org.navistack.admin.support.validation.groups.Create;
import org.navistack.admin.support.validation.groups.Modify;

import javax.validation.constraints.*;

@Data
public class DictDto {
    protected static final String CODE_PATTERN = "^[A-Za-z0-9$_]+$";

    @Null(groups = Create.class)
    @NotNull(groups = Modify.class)
    private Long id;

    @NotEmpty()
    @Size(min = 1, max = 48)
    @Pattern(regexp = CODE_PATTERN, message = "{Pattern.Dict.code}")
    private String code;

    @Size(max = 80)
    private String name;

    @Size(max = 140)
    private String description;
}
