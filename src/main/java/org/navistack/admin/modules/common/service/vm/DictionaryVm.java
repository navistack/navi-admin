package org.navistack.admin.modules.common.service.vm;

import lombok.Data;

@Data
public class DictionaryVm {
    private Long id;

    private String code;

    private String name;

    private String remarks;
}
