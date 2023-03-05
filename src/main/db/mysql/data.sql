-- ----------------------------------------
-- Privilege, aka function
-- ----------------------------------------
INSERT INTO `privilege` (`id`, `code`, `name`, `remarks`, `parent_id`)
VALUES (1, 'sys:dictionary:query', 'sys:dictionary:query', 'Query Paged list of dictionaries', NULL),
       (2, 'sys:dictionary:create', 'sys:dictionary:create', 'Create a dictionary', NULL),
       (3, 'sys:dictionary:modify', 'sys:dictionary:modify', 'Modify dictionary', NULL),
       (4, 'sys:dictionary:remove', 'sys:dictionary:remove', 'Remove dictionary', NULL),
       (5, 'sys:dictionary:queryitem', 'sys:dictionary:queryitem', 'Query Paged list of dictionary items', NULL),
       (6, 'sys:dictionary:createitem', 'sys:dictionary:createitem', 'Create a dictionary item', NULL),
       (7, 'sys:dictionary:modifyitem', 'sys:dictionary:modifyitem', 'Modify dictionary item', NULL),
       (8, 'sys:dictionary:removeitem', 'sys:dictionary:removeitem', 'Remove dictionary item', NULL),
       (9, 'sys:region:query', 'sys:region:query', 'Query Paged list of regions', NULL),
       (10, 'sys:region:create', 'sys:region:create', 'Create a region', NULL),
       (11, 'sys:region:modify', 'sys:region:modify', 'Modify region', NULL),
       (12, 'sys:region:remove', 'sys:region:remove', 'Remove region', NULL),

       (13, 'sys:privilege:query', 'sys:privilege:query', 'Query Paged list of privileges', NULL),
       (14, 'sys:privilege:create', 'sys:privilege:create', 'Create a privilege', NULL),
       (15, 'sys:privilege:modify', 'sys:privilege:modify', 'Modify privilege', NULL),
       (16, 'sys:privilege:remove', 'sys:privilege:remove', 'Remove privilege', NULL),
       (17, 'sys:role:query', 'sys:role:query', 'Query Paged list of roles', NULL),
       (18, 'sys:role:querydetail', 'sys:role:querydetail', 'Query details about role', NULL),
       (19, 'sys:role:create', 'sys:role:create', 'Create a role', NULL),
       (20, 'sys:role:modify', 'sys:role:modify', 'Modify role', NULL),
       (21, 'sys:role:remove', 'sys:role:remove', 'Remove role', NULL),
       (22, 'sys:user:query', 'sys:user:query', 'Query Paged list of users', NULL),
       (23, 'sys:user:querydetail', 'sys:user:querydetail', 'Query details about user', NULL),
       (24, 'sys:user:create', 'sys:user:create', 'Create a user', NULL),
       (25, 'sys:user:modify', 'sys:user:modify', 'Modify user', NULL),
       (26, 'sys:user:remove', 'sys:user:remove', 'Remove user', NULL),
       (27, 'sys:organization:query', 'sys:organization:query', 'Query Paged list of organizations', NULL),
       (28, 'sys:organization:create', 'sys:organization:create', 'Create an organization', NULL),
       (29, 'sys:organization:modify', 'sys:organization:modify', 'Modify organization', NULL),
       (30, 'sys:organization:remove', 'sys:organization:remove', 'Remove organization', NULL);

-- ----------------------------------------
-- Role
-- ----------------------------------------

INSERT INTO `role` (`id`, `code`, `name`, `remarks`)
VALUES (1, 'Admin', 'Administrator', 'Administrator User');

-- ----------------------------------------
-- Role's privileges, aka permissions
-- ----------------------------------------

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

-- ----------------------------------------
-- User
-- ----------------------------------------

INSERT INTO `user` (`id`, `nick_name`, `login_name`, `password`)
VALUES (1, 'Admin', 'admin', '$2a$10$F0lnYRi2KUd9Nsyu.TF6Mu3jwUrN3sYZHFFyPIp1VNrJAYucBqT/m');

-- ----------------------------------------
-- Roles of user
-- ----------------------------------------

INSERT INTO `user_role` (`user_id`, `role_id`)
VALUES (1, 1);
