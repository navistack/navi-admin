package org.navistack.admin.modules.common.service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.navistack.admin.support.validation.groups.Create;
import org.navistack.admin.support.validation.groups.Modify;

@Data
public class DictionaryItemDto {
    @Null(groups = Create.class)
    @NotNull(groups = Modify.class)
    private Long id;

    @NotEmpty
    @Pattern(regexp = "^[A-Za-z0-9$_]{1,48}$", message = "{validation.constraints.DictionaryItem.code.message}")
    private String code;

    @Size(max = 80)
    private String name;

    @NotNull
    private Long dictionaryId;

    @Size(max = 140)
    private String remarks;
}