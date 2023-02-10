# ########################################
# Privilege, aka function
# ########################################

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
    `remarks`    VARCHAR(140),

    UNIQUE KEY (`code`)
);

# ########################################
# Role
# ########################################

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
    `remarks`    VARCHAR(140),

    UNIQUE KEY (`code`)
);

# ########################################
# Role's privileges, aka permissions
# ########################################

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
    `remarks`      VARCHAR(140),

    UNIQUE KEY (`role_id`, `privilege_id`)
);

# ########################################
# Organization
# ########################################

CREATE TABLE `organization`
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

# ########################################
# User
# ########################################

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

    `created_at`    DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by`    BIGINT,
    `updated_at`    DATETIME ON UPDATE CURRENT_TIMESTAMP,
    `updated_by`    BIGINT,
    `deleted`       BOOLEAN              DEFAULT FALSE,
    `deleted_at`    DATETIME,
    `deleted_by`    BIGINT,
    `remarks`       VARCHAR(140),

    UNIQUE KEY (`login_name`),
    UNIQUE KEY (`mobile_number`),
    UNIQUE KEY (`email_address`)
);

# ########################################
# Roles of user
# ########################################

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
    `remarks`    VARCHAR(140),

    UNIQUE KEY (`user_id`, `role_id`)
);

# ########################################
# Organizations of user
# ########################################

CREATE TABLE `user_organization`
(
    `id`              BIGINT   NOT NULL PRIMARY KEY AUTO_INCREMENT,

    `user_id`         BIGINT,
    `organization_id` BIGINT,

    `created_at`      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by`      BIGINT,
    `updated_at`      DATETIME ON UPDATE CURRENT_TIMESTAMP,
    `updated_by`      BIGINT,
    `deleted`         BOOLEAN           DEFAULT FALSE,
    `deleted_at`      DATETIME,
    `deleted_by`      BIGINT,
    `remarks`         VARCHAR(140)
);

# ########################################
# Dictionaries
# ########################################

CREATE TABLE `dictionary`
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

CREATE TABLE `dictionary_item`
(
    `id`              BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,

    `code`            CHAR(48)    NOT NULL,
    `name`            VARCHAR(80) NOT NULL,

    `dictionary_code` CHAR(48)    NOT NULL,

    `created_at`      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by`      BIGINT,
    `updated_at`      DATETIME ON UPDATE CURRENT_TIMESTAMP,
    `updated_by`      BIGINT,
    `deleted`         BOOLEAN              DEFAULT FALSE,
    `deleted_at`      DATETIME,
    `deleted_by`      BIGINT,
    `remarks`         VARCHAR(140),

    INDEX (`dictionary_code`)
);

# ########################################
# Regions
# ########################################

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
    `remarks`     VARCHAR(140),

    INDEX (`code`),
    INDEX (`parent_code`)
);
