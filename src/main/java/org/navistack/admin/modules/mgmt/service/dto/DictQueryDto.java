package org.navistack.admin.modules.mgmt.service.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class DictQueryDto {
    private Long id;

    @Pattern(regexp = "^[A-Za-z0-9$_]{1,48}$", message = "{validation.constraints.Dict.code.message}")
    private String code;

    private String name;
}
