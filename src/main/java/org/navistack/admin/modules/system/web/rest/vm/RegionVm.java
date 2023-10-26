package org.navistack.admin.modules.system.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.navistack.framework.data.TreeNode;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class RegionVm implements TreeNode<String, RegionVm> {
    private String code;

    private String name;

    private String parentCode;

    private Collection<RegionVm> subRegions;

    public RegionVm() {
        subRegions = new ArrayList<>();
    }

    @JsonIgnore
    @Override
    public String getId() {
        return getParentCode();
    }

    @Override
    public void setId(String code) {
        setCode(code);
    }

    @JsonIgnore
    @Override
    public String getParentId() {
        return getParentCode();
    }

    @Override
    public void setParentId(String parentCode) {
        setParentCode(parentCode);
    }

    @JsonIgnore
    @Override
    public Collection<RegionVm> getChildren() {
        return getSubRegions();
    }

    @Override
    public void setChildren(Collection<RegionVm> subRegions) {
        setSubRegions(subRegions);
    }

    @Override
    public void addChild(RegionVm subRegion) {
        subRegions.add(subRegion);
    }

    @Override
    public void addChildren(Collection<? extends RegionVm> subRegions) {
        this.subRegions.addAll(subRegions);
    }
}
