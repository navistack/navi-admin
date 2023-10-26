package org.navistack.admin.modules.common.dtobj;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.navistack.admin.support.mybatis.IdentifyingDataObject;

@EqualsAndHashCode(callSuper = true)
@Data
public class RegionDo extends IdentifyingDataObject<Long> {
    /**
     * Region code.
     *
     * @see <a href="https://en.wikipedia.org/wiki/ISO_3166-2">ISO3166-2</a>
     */
    private String code;

    private String name;

    private String parentCode;

    private String remarks;
}
