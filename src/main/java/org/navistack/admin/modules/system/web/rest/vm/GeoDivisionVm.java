package org.navistack.admin.modules.system.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.navistack.admin.modules.common.entity.GeoDivision;
import org.navistack.framework.data.TreeNode;

import java.util.Collection;
import java.util.LinkedList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeoDivisionVm implements TreeNode<String, GeoDivisionVm> {
    private String code;

    private String name;

    private String parentCode;

    private Collection<GeoDivisionVm> subDivisions;

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
    public Collection<GeoDivisionVm> getChildren() {
        return subDivisions;
    }

    public static GeoDivisionVm of(String code, String name, String parentCode) {
        return new GeoDivisionVm(code, name, parentCode, new LinkedList<>());
    }

    public static GeoDivisionVm of(GeoDivision entity) {
        return of(entity.getCode(), entity.getName(), entity.getParentCode());
    }
}
