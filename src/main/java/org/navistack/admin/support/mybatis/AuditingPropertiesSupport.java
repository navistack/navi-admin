package org.navistack.admin.support.mybatis;

import lombok.experimental.UtilityClass;
import org.navistack.admin.support.security.AuthContext;
import org.navistack.admin.support.security.LoginUser;
import org.navistack.framework.data.AuditingProperties;

import java.time.Instant;

@UtilityClass
public class AuditingPropertiesSupport {
    public void created(AuditingProperties<Long> entity) {
        entity.setCreatedAt(Instant.now());

        AuthContext.currentUser()
                .map(LoginUser::getId)
                .ifPresent(entity::setCreatedBy);
    }

    public void updated(AuditingProperties<Long> entity) {
        entity.setUpdatedAt(Instant.now());

        AuthContext.currentUser()
                .map(LoginUser::getId)
                .ifPresent(entity::setUpdatedBy);
    }

    public void deleted(AuditingProperties<Long> entity) {
        entity.setDeletedAt(Instant.now());

        AuthContext.currentUser()
                .map(LoginUser::getId)
                .ifPresent(entity::setDeletedBy);
    }
}
