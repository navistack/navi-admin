package org.navistack.admin.support.mybatis;

import lombok.experimental.UtilityClass;
import org.navistack.admin.support.security.AuthContext;
import org.navistack.admin.support.security.LoginUser;
import org.navistack.framework.batis.entity.AuditingEntity;

import java.time.Instant;
import java.util.Optional;

@UtilityClass
public class AuditingEntitySupport {
    public void insertAuditingProperties(AuditingEntity<Long> entity) {
        Instant now = Instant.now();
        entity.setCreatedAt(now);

        Optional<LoginUser> loginUser = AuthContext.currentUser();
        if (loginUser.isPresent()) {
            Long userId = loginUser.get().getId();
            entity.setCreatedBy(userId);
        }
    }

    public void updateAuditingProperties(AuditingEntity<Long> entity) {
        Instant now = Instant.now();
        entity.setUpdatedAt(now);

        Optional<LoginUser> loginUser = AuthContext.currentUser();
        if (loginUser.isPresent()) {
            Long userId = loginUser.get().getId();
            entity.setUpdatedBy(userId);
        }
    }
}
