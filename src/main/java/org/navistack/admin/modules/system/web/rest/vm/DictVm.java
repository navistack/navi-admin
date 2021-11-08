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
public class DictVm {
    private String code;

    private String name;

    private Collection<DictItemVm> items;

    public static DictVm of(String code, String name) {
        return new DictVm(code, name, new LinkedList<>());
    }
}
