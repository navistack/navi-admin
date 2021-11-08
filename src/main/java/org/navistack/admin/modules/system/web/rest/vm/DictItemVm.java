package org.navistack.admin.modules.system.web.rest.vm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictItemVm {
    private String name;

    private String key;

    private String value;

    public static DictItemVm of(String name, String key, String value) {
        return new DictItemVm(name, key, value);
    }

    public static DictItemVm of() {
        return new DictItemVm();
    }
}
