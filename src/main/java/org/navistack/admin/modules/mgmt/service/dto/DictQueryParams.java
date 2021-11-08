package org.navistack.admin.modules.mgmt.service.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class DictQueryParams {
    protected static final String CODE_PATTERN = "^[A-Za-z0-9$_]+$";

    private Long id;

    @Pattern(regexp = CODE_PATTERN, message = "{Pattern.Dict.code}")
    private String code;

    private String name;
}
