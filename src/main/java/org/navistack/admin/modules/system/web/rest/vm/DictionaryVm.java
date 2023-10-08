package org.navistack.admin.modules.system.web.rest.vm;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class DictionaryVm {
    private String code;

    private String name;

    private Collection<DictionaryItemVm> items;

    public DictionaryVm() {
        items = new ArrayList<>();
    }
}
