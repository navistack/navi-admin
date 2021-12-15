package org.navistack.admin.modules.mgmt.service.dto;

import lombok.Data;
import org.navistack.framework.crudsupport.validation.groups.Create;
import org.navistack.framework.crudsupport.validation.groups.Modify;

import javax.validation.constraints.*;

@Data
public class DictItemDto {
    protected static final String CODE_PATTERN = "^[A-Za-z0-9$_]+$";

    @Null(groups = Create.class)
    @NotNull(groups = Modify.class)
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 36)
    private String name;

    @NotEmpty
    @Size(min = 1, max = 48)
    @Pattern(regexp = CODE_PATTERN, message = "{Pattern.DictItem.itKey}")
    private String itKey;

    @NotEmpty
    @Size(min = 1, max = 80)
    private String itValue;

    @Size(max = 140)
    private String description;

    @NotEmpty
    private String dictCode;
}
