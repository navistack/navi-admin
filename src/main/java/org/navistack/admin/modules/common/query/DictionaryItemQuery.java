package org.navistack.admin.modules.common.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DictionaryItemQuery {
    private Long id;

    private String code;

    private String name;

    private String dictionaryCode;
}
