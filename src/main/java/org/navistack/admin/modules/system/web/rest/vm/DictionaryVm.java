package org.navistack.admin.modules.system.web.rest.vm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.LinkedList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DictionaryVm {
    private String code;

    private String name;

    private Collection<DictionaryItemVm> items;

    public static DictionaryVm of(String code, String name) {
        return new DictionaryVm(code, name, new LinkedList<>());
    }
}
