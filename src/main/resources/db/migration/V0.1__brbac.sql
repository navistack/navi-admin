# ########################################
# Privilege, aka function
# ########################################

CREATE TABLE `privilege`
(
    `id`          BIGINT   NOT NULL PRIMARY KEY AUTO_INCREMENT,

    `code`        CHAR(48) NOT NULL,
    `name`        VARCHAR(80),
    `description` VARCHAR(140),

    `parent_id`   BIGINT,

    `created_at`  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at`  DATETIME,
    `created_by`  BIGINT,
    `updated_by`  BIGINT,
    `deleted_by`  BIGINT,

    UNIQUE KEY (`code`)
);

# ########################################
# Role
# ########################################

CREATE TABLE `role`
(
    `id`          BIGINT   NOT NULL PRIMARY KEY AUTO_INCREMENT,

    `code`        CHAR(24) NOT NULL,
    `name`        VARCHAR(80),
    `description` VARCHAR(140),

    `created_at`  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at`  DATETIME,
    `created_by`  BIGINT,
    `updated_by`  BIGINT,
    `deleted_by`  BIGINT,

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
    `updated_at`   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at`   DATETIME,
    `created_by`   BIGINT,
    `updated_by`   BIGINT,
    `deleted_by`   BIGINT,

    UNIQUE KEY (`role_id`, `privilege_id`)
);

# ########################################
# Organization
# ########################################

CREATE TABLE `org`
(
    `id`          BIGINT   NOT NULL PRIMARY KEY AUTO_INCREMENT,

    `code`        CHAR(48) NOT NULL,
    `name`        VARCHAR(80),
    `super_id`    BIGINT,

    `description` VARCHAR(140),

    `created_at`  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at`  DATETIME,
    `created_by`  BIGINT,
    `updated_by`  BIGINT,
    `deleted_by`  BIGINT
);

# ########################################
# User
# ########################################

CREATE TABLE `user`
(
    `id`            BIGINT      NOT NULL PRIMARY KEY,

    `nick_name`     VARCHAR(12),
    `avatar_url`    VARCHAR(240),

    `gender`        VARCHAR(48),
    `birthday`      DATE,

    `login_name`    VARCHAR(24) NOT NULL,

    `mobile_prefix` CHAR(3),
    `mobile_number` CHAR(14),
    `email_address` VARCHAR(42),

    `password`      CHAR(80),

    `status`        CHAR(48),

    `org_id`        BIGINT,

    `created_at`    DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at`    DATETIME,
    `created_by`    BIGINT,
    `updated_by`    BIGINT,
    `deleted_by`    BIGINT,

    UNIQUE KEY (`login_name`),
    UNIQUE KEY (`mobile_prefix`, `mobile_number`),
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
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` DATETIME,
    `created_by` BIGINT,
    `updated_by` BIGINT,
    `deleted_by` BIGINT,

    UNIQUE KEY (`user_id`, `role_id`)
);

# ########################################
# Data
# ########################################

INSERT INTO `privilege` (`id`, `code`, `name`, `description`, `parent_id`)
VALUES (1, 'sys:dict:paginate', 'sys:dict:paginate', 'Query Paged list of dictionaries', NULL),
       (2, 'sys:dict:create', 'sys:dict:create', 'Create a dictionary', NULL),
       (3, 'sys:dict:modify', 'sys:dict:modify', 'Modify dictionary', NULL),
       (4, 'sys:dict:remove', 'sys:dict:remove', 'Remove dictionary', NULL),
       (5, 'sys:dict_item:paginate', 'sys:dict_item:paginate', 'Query Paged list of dictionary items', NULL),
       (6, 'sys:dict_item:create', 'sys:dict_item:create', 'Create a dictionary item', NULL),
       (7, 'sys:dict_item:modify', 'sys:dict_item:modify', 'Modify dictionary item', NULL),
       (8, 'sys:dict_item:remove', 'sys:dict_item:remove', 'Remove dictionary item', NULL),
       (9, 'sys:geo_division:paginate', 'sys:geo_division:paginate', 'Query Paged list of geographical divisions',
        NULL),
       (10, 'sys:geo_division:create', 'sys:geo_division:create', 'Create a geographical division', NULL),
       (11, 'sys:geo_division:modify', 'sys:geo_division:modify', 'Modify geographical division', NULL),
       (12, 'sys:geo_division:remove', 'sys:geo_division:remove', 'Remove geographical division', NULL),

       (13, 'sys:privilege:paginate', 'sys:privilege:paginate', 'Query Paged list of privileges', NULL),
       (14, 'sys:privilege:create', 'sys:privilege:create', 'Create a privilege', NULL),
       (15, 'sys:privilege:modify', 'sys:privilege:modify', 'Modify privilege', NULL),
       (16, 'sys:privilege:remove', 'sys:privilege:remove', 'Remove privilege', NULL),
       (17, 'sys:role:paginate', 'sys:role:paginate', 'Query Paged list of roles', NULL),
       (18, 'sys:role:detail', 'sys:role:detail', 'Query details about role', NULL),
       (19, 'sys:role:create', 'sys:role:create', 'Create a role', NULL),
       (20, 'sys:role:modify', 'sys:role:modify', 'Modify role', NULL),
       (21, 'sys:role:remove', 'sys:role:remove', 'Remove role', NULL),
       (22, 'sys:user:paginate', 'sys:user:paginate', 'Query Paged list of users', NULL),
       (23, 'sys:user:detail', 'sys:user:detail', 'Query details about user', NULL),
       (24, 'sys:user:create', 'sys:user:create', 'Create a user', NULL),
       (25, 'sys:user:modify', 'sys:user:modify', 'Modify user', NULL),
       (26, 'sys:user:remove', 'sys:user:remove', 'Remove user', NULL),
       (27, 'sys:organization:paginate', 'sys:organization:paginate', 'Query Paged list of organizations', NULL),
       (28, 'sys:organization:create', 'sys:organization:create', 'Create an organization', NULL),
       (29, 'sys:organization:modify', 'sys:organization:modify', 'Modify organization', NULL),
       (30, 'sys:organization:remove', 'sys:organization:remove', 'Remove organization', NULL);

INSERT INTO `role` (`id`, `code`, `name`, `description`)
VALUES (1, 'Admin', 'Administrator', 'Administrator User');

INSERT INTO `role_privilege` (`role_id`, `privilege_id`)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (1, 10),
       (1, 11),
       (1, 12),
       (1, 13),
       (1, 14),
       (1, 15),
       (1, 16),
       (1, 17),
       (1, 18),
       (1, 19),
       (1, 20),
       (1, 21),
       (1, 22),
       (1, 23),
       (1, 24),
       (1, 25),
       (1, 26),
       (1, 27),
       (1, 28),
       (1, 29),
       (1, 30);

INSERT INTO `user` (`id`, `nick_name`, `login_name`, `password`)
VALUES (1, 'Admin', 'admin', '$2a$10$F0lnYRi2KUd9Nsyu.TF6Mu3jwUrN3sYZHFFyPIp1VNrJAYucBqT/m');

INSERT INTO `user_role` (`user_id`, `role_id`)
VALUES (1, 1);