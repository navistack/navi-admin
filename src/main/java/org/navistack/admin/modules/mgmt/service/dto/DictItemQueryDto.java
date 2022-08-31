package org.navistack.admin.modules.mgmt.service.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class DictItemQueryDto {
    private Long id;

    private String name;

    @Pattern(regexp = "^[A-Za-z0-9$_]{1,48}$", message = "{Pattern.DictItem.itKey}")
    private String itKey;

    private String dictCode;
}
