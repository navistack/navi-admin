package org.navistack.admin.modules.identity.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.identity.entity.Role;
import org.navistack.admin.modules.identity.service.vm.RoleDetailVm;

@Mapper
public interface RoleDetailVmConverter {
    RoleDetailVmConverter INSTANCE = Mappers.getMapper(RoleDetailVmConverter.class);

    RoleDetailVm fromEntity(Role entity);
}
