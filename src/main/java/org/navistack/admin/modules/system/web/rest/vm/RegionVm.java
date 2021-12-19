package org.navistack.admin.modules.system.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.navistack.admin.modules.common.entity.Region;
import org.navistack.framework.data.TreeNode;

import java.util.Collection;
import java.util.LinkedList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionVm implements TreeNode<String, RegionVm> {
    private String code;

    private String name;

    private String parentCode;

    private Collection<RegionVm> subRegions;

    @JsonIgnore
    @Override
    public String getId() {
        return code;
    }

    @JsonIgnore
    @Override
    public String getParentId() {
        return parentCode;
    }

    @JsonIgnore
    @Override
    public Collection<RegionVm> getChildren() {
        return subRegions;
    }

    public static RegionVm of(String code, String name, String parentCode) {
        return new RegionVm(code, name, parentCode, new LinkedList<>());
    }

    public static RegionVm of(Region entity) {
        return of(entity.getCode(), entity.getName(), entity.getParentCode());
    }
}
