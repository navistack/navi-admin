# ########################################
# Dictionaries
# ########################################

CREATE TABLE `dict`
(
    `id`          BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,

    `code`        CHAR(48)    NOT NULL,
    `name`        VARCHAR(80) NOT NULL,
    `description` VARCHAR(140),

    `created_at`  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at`  DATETIME,
    `created_by`  BIGINT,
    `updated_by`  BIGINT,
    `deleted_by`  BIGINT,

    UNIQUE KEY (`code`)
);

CREATE TABLE `dict_item`
(
    `id`          BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,

    `name`        VARCHAR(80) NOT NULL,
    `it_key`      CHAR(48)    NOT NULL,
    `it_value`    VARCHAR(80) NOT NULL,
    `description` VARCHAR(140),

    `dict_code`   CHAR(48)    NOT NULL,

    `created_at`  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at`  DATETIME,
    `created_by`  BIGINT,
    `updated_by`  BIGINT,
    `deleted_by`  BIGINT,

    UNIQUE KEY (`dict_code`, `it_key`),
    INDEX (`it_key`)
);

# ########################################
# Geo Regions
# ########################################

CREATE TABLE `geo_division`
(
    `id`          BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,

    `code`        CHAR(48)    NOT NULL,
    `name`        VARCHAR(80) NOT NULL,
    `parent_code` CHAR(48),
    `description` VARCHAR(140),

    `created_at`  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at`  DATETIME,
    `created_by`  BIGINT,
    `updated_by`  BIGINT,
    `deleted_by`  BIGINT,

    INDEX (`code`),
    INDEX (`parent_code`)
);
