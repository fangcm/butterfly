DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `mobile` varchar(11) NOT NULL COMMENT '手机',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `email` varchar(255) NOT NULL DEFAULT '' COMMENT '邮箱',
  `nick_name` varchar(32) NOT NULL DEFAULT '' COMMENT '昵称',
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态 0正常 1禁用',
  `remarks` varchar(255) NOT NULL DEFAULT '' COMMENT '备注信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) NOT NULL DEFAULT '' COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) NOT NULL DEFAULT '' COMMENT '更新者',
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除标志（0-正常，1-已删除）',
  PRIMARY KEY (`id`),
  KEY `idx_mobile` (`mobile`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统-用户信息表';

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '角色名称',
  `role_code` varchar(16) NOT NULL DEFAULT '' COMMENT '角色代码，例如 admin',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) NOT NULL DEFAULT '' COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) NOT NULL DEFAULT '' COMMENT '更新者',
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除标志（0-正常，1-已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统-角色表';

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `role_id` varchar(32) NOT NULL COMMENT '角色ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) NOT NULL DEFAULT '' COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) NOT NULL DEFAULT '' COMMENT '更新者',
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除标志（0-正常，1-已删除）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_roleId_userId` (`role_id`,`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统-用户角色表';

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '菜单名称',
  `icon` varchar(255) NOT NULL DEFAULT '' COMMENT '图标',
  `path` varchar(255) NOT NULL DEFAULT '' COMMENT '路径',
  `root_level` bit(1) NOT NULL DEFAULT b'0' COMMENT '根级菜单',
  `parent_id` varchar(32) NOT NULL DEFAULT '' COMMENT '父菜单ID',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序（升序）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) NOT NULL DEFAULT '' COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) NOT NULL DEFAULT '' COMMENT '更新者',
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除标志（0-正常，1-已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统-菜单表';

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `role_id` varchar(32) NOT NULL COMMENT '角色ID',
  `menu_id` varchar(32) NOT NULL COMMENT '菜单ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) NOT NULL DEFAULT '' COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) NOT NULL DEFAULT '' COMMENT '更新者',
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除标志（0-正常，1-已删除）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_roleId_menuId` (`role_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统-角色菜单表';

