package org.navistack.admin.modules.common.dtobj;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.navistack.admin.support.mybatis.IdentifyingDataObject;

@EqualsAndHashCode(callSuper = true)
@Data
public class DictionaryDo extends IdentifyingDataObject<Long> {
    private String code;

    private String name;

    private String remarks;
}
