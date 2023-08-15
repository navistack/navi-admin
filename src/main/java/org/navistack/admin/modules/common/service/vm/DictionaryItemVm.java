package org.navistack.admin.modules.common.service.vm;

import lombok.Data;

@Data
public class DictionaryItemVm {
    private Long id;

    private String code;

    private String name;

    private Long dictionaryId;

    private String remarks;
}
