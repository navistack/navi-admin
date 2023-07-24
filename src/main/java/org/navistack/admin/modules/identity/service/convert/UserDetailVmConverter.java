package org.navistack.admin.modules.identity.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.identity.entity.User;
import org.navistack.admin.modules.identity.service.vm.UserDetailVm;

@Mapper
public interface UserDetailVmConverter {
    UserDetailVmConverter INSTANCE = Mappers.getMapper(UserDetailVmConverter.class);

    UserDetailVm fromEntity(User entity);
}
