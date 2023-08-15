package org.navistack.admin.modules.identity.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.identity.dtobj.UserDo;
import org.navistack.admin.modules.identity.service.vm.UserVm;

@Mapper
public interface UserVmConvert {
    UserVmConvert INSTANCE = Mappers.getMapper(UserVmConvert.class);

    UserVm from(UserDo dtObj);
}
