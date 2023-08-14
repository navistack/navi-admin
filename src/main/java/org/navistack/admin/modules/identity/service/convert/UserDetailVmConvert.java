package org.navistack.admin.modules.identity.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.identity.dtobj.UserDo;
import org.navistack.admin.modules.identity.service.vm.UserDetailVm;

@Mapper
public interface UserDetailVmConvert {
    UserDetailVmConvert INSTANCE = Mappers.getMapper(UserDetailVmConvert.class);

    UserDetailVm from(UserDo dtObj);
}
