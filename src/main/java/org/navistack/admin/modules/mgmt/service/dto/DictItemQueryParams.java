package org.navistack.admin.modules.mgmt.service.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class DictItemQueryParams {
    protected static final String CODE_PATTERN = "^[A-Za-z0-9$_]+$";

    private Long id;

    private String name;

    @Pattern(regexp = CODE_PATTERN, message = "{Pattern.DictItem.itKey}")
    private String itKey;

    private String dictCode;
}
