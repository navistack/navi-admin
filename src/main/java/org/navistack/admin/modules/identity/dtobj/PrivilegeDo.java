package org.navistack.admin.modules.identity.dtobj;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.navistack.admin.support.mybatis.IdentifyingDataObject;

@EqualsAndHashCode(callSuper = true)
@Data
public class PrivilegeDo extends IdentifyingDataObject<Long> {
    private String code;

    private String name;

    private Long parentId;

    private String remarks;
}
