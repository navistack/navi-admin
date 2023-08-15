package org.navistack.admin.modules.common.service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DictionaryItemCreateDto {
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
