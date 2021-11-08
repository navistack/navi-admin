package org.navistack.admin.support.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.navistack.admin.support.security.AuthContext;

import java.time.Instant;

public class AuditingHandler implements MetaObjectHandler {
    protected static final String CREATED_AT_FIELD = "createdAt";
    protected static final String UPDATED_AT_FIELD = "updatedAt";
    protected static final String CREATED_BY_FIELD = "createdBy";
    protected static final String UPDATED_BY_FIELD = "updatedBy";

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, CREATED_AT_FIELD, Instant.class, Instant.now());
        this.strictInsertFill(metaObject, UPDATED_AT_FIELD, Instant.class, Instant.now());

        AuthContext.currentUser()
                .ifPresent(user -> {
                    Long userId = user.getId();
                    this.strictInsertFill(metaObject, CREATED_BY_FIELD, Long.class, userId);
                    this.strictInsertFill(metaObject, UPDATED_BY_FIELD, Long.class, userId);
                });
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, UPDATED_AT_FIELD, Instant.class, Instant.now());

        AuthContext.currentUser()
                .ifPresent(user -> this.strictInsertFill(metaObject, UPDATED_BY_FIELD, Long.class, user.getId()));
    }
}
