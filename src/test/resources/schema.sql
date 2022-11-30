CREATE TABLE `privilege`
(
    `id`         BIGINT   NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `code`       CHAR(48) NOT NULL,
    `name`       VARCHAR(80),
    `parent_id`  BIGINT,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by` BIGINT,
    `updated_at` DATETIME ON UPDATE CURRENT_TIMESTAMP,
    `updated_by` BIGINT,
    `deleted`    BOOLEAN           DEFAULT FALSE,
    `deleted_at` DATETIME,
    `deleted_by` BIGINT,
    `remarks`    VARCHAR(140)
);

CREATE TABLE `role`
(
    `id`         BIGINT   NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `code`       CHAR(24) NOT NULL,
    `name`       VARCHAR(80),
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by` BIGINT,
    `updated_at` DATETIME ON UPDATE CURRENT_TIMESTAMP,
    `updated_by` BIGINT,
    `deleted`    BOOLEAN           DEFAULT FALSE,
    `deleted_at` DATETIME,
    `deleted_by` BIGINT,
    `remarks`    VARCHAR(140)
);

CREATE TABLE `role_privilege`
(
    `id`           BIGINT   NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `role_id`      BIGINT   NOT NULL,
    `privilege_id` BIGINT   NOT NULL,
    `created_at`   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by`   BIGINT,
    `updated_at`   DATETIME ON UPDATE CURRENT_TIMESTAMP,
    `updated_by`   BIGINT,
    `deleted`      BOOLEAN           DEFAULT FALSE,
    `deleted_at`   DATETIME,
    `deleted_by`   BIGINT,
    `remarks`      VARCHAR(140)
);

CREATE TABLE `org`
(
    `id`         BIGINT   NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `code`       CHAR(48) NOT NULL,
    `name`       VARCHAR(80),
    `super_id`   BIGINT,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by` BIGINT,
    `updated_at` DATETIME ON UPDATE CURRENT_TIMESTAMP,
    `updated_by` BIGINT,
    `deleted`    BOOLEAN           DEFAULT FALSE,
    `deleted_at` DATETIME,
    `deleted_by` BIGINT,
    `remarks`    VARCHAR(140)
);

CREATE TABLE `user`
(
    `id`            BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `nick_name`     VARCHAR(12),
    `avatar_url`    VARCHAR(240),
    `gender`        VARCHAR(48),
    `birthday`      DATE,
    `login_name`    VARCHAR(24) NOT NULL,
    `mobile_number` CHAR(17),
    `email_address` VARCHAR(42),
    `password`      CHAR(80),
    `status`        CHAR(48),
    `org_id`        BIGINT,
    `created_at`    DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by`    BIGINT,
    `updated_at`    DATETIME ON UPDATE CURRENT_TIMESTAMP,
    `updated_by`    BIGINT,
    `deleted`       BOOLEAN              DEFAULT FALSE,
    `deleted_at`    DATETIME,
    `deleted_by`    BIGINT,
    `remarks`       VARCHAR(140)
);

CREATE TABLE `user_role`
(
    `id`         BIGINT   NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `user_id`    BIGINT,
    `role_id`    BIGINT,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by` BIGINT,
    `updated_at` DATETIME ON UPDATE CURRENT_TIMESTAMP,
    `updated_by` BIGINT,
    `deleted`    BOOLEAN           DEFAULT FALSE,
    `deleted_at` DATETIME,
    `deleted_by` BIGINT,
    `remarks`    VARCHAR(140)
);

CREATE TABLE `dict`
(
    `id`         BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `code`       CHAR(48)    NOT NULL,
    `name`       VARCHAR(80) NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by` BIGINT,
    `updated_at` DATETIME ON UPDATE CURRENT_TIMESTAMP,
    `updated_by` BIGINT,
    `deleted`    BOOLEAN              DEFAULT FALSE,
    `deleted_at` DATETIME,
    `deleted_by` BIGINT,
    `remarks`    VARCHAR(140)
);

CREATE TABLE `dict_item`
(
    `id`         BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name`       VARCHAR(80) NOT NULL,
    `it_key`     CHAR(48)    NOT NULL,
    `it_value`   VARCHAR(80) NOT NULL,
    `dict_code`  CHAR(48)    NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by` BIGINT,
    `updated_at` DATETIME ON UPDATE CURRENT_TIMESTAMP,
    `updated_by` BIGINT,
    `deleted`    BOOLEAN              DEFAULT FALSE,
    `deleted_at` DATETIME,
    `deleted_by` BIGINT,
    `remarks`    VARCHAR(140)
);

CREATE TABLE `region`
(
    `id`          BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `code`        CHAR(48)    NOT NULL,
    `name`        VARCHAR(80) NOT NULL,
    `parent_code` CHAR(48),
    `created_at`  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by`  BIGINT,
    `updated_at`  DATETIME ON UPDATE CURRENT_TIMESTAMP,
    `updated_by`  BIGINT,
    `deleted`     BOOLEAN              DEFAULT FALSE,
    `deleted_at`  DATETIME,
    `deleted_by`  BIGINT,
    `remarks`     VARCHAR(140)
);
