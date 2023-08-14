package org.navistack.admin.support.mybatis;

import lombok.experimental.UtilityClass;
import org.navistack.admin.support.security.AuthContext;
import org.navistack.admin.support.security.LoginUser;
import org.navistack.framework.data.AuditingProperties;

import java.time.Instant;

@UtilityClass
public class AuditingPropertiesSupport {
    public void created(AuditingProperties<Long> obj) {
        obj.setCreatedAt(Instant.now());

        AuthContext.currentUser()
                .map(LoginUser::getId)
                .ifPresent(obj::setCreatedBy);
    }

    public void updated(AuditingProperties<Long> obj) {
        obj.setUpdatedAt(Instant.now());

        AuthContext.currentUser()
                .map(LoginUser::getId)
                .ifPresent(obj::setUpdatedBy);
    }

    public void deleted(AuditingProperties<Long> obj) {
        obj.setDeletedAt(Instant.now());

        AuthContext.currentUser()
                .map(LoginUser::getId)
                .ifPresent(obj::setDeletedBy);
    }
}
