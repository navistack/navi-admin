package org.navistack.admin.modules.system.web.rest.vm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(staticName = "of")
public class DictionaryItemVm {
    private String code;

    private String name;
}
