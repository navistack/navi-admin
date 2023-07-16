package org.navistack.admin.modules.system.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.navistack.framework.data.TreeNode;

import java.util.Collection;

@Data
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
}
