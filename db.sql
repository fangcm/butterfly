CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `mobile` varchar(11) NOT NULL COMMENT '�ֻ�',
  `password` varchar(255) NOT NULL COMMENT '����',
  `email` varchar(255) NOT NULL DEFAULT '' COMMENT '����',
  `nick_name` varchar(32) NOT NULL DEFAULT '' COMMENT '�ǳ�',
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '״̬ 0���� 1����',
  `remarks` varchar(255) NOT NULL DEFAULT '' COMMENT '��ע��Ϣ',
  `create_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `create_by` varchar(32) NOT NULL DEFAULT '' COMMENT '������',
  `update_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `update_by` varchar(32) NOT NULL DEFAULT '' COMMENT '������',
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT 'ɾ����־��0-������1-��ɾ����',
  PRIMARY KEY (`id`),
  KEY `idx_mobile` (`mobile`,`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ϵͳ-�û���Ϣ��';


CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '��ɫ�� ��ROLE_��ͷ',
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT '��ɫ��ʾ����',
  `access` int(11) NOT NULL DEFAULT '0' COMMENT '��ӦȨ��ֵ Ȩ�޲˵�����',
  `create_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `create_by` varchar(32) NOT NULL DEFAULT '' COMMENT '������',
  `update_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `update_by` varchar(32) NOT NULL DEFAULT '' COMMENT '������',
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT 'ɾ����־��0-������1-��ɾ����',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ϵͳ-��ɫ��';


CREATE TABLE `sys_user_role` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `user_id` varchar(32) NOT NULL COMMENT '�û�ID',
  `role_id` varchar(32) NOT NULL COMMENT '��ɫID',
  `create_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `create_by` varchar(32) NOT NULL DEFAULT '' COMMENT '������',
  `update_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `update_by` varchar(32) NOT NULL DEFAULT '' COMMENT '������',
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT 'ɾ����־��0-������1-��ɾ����',
  PRIMARY KEY (`id`),
  KEY `idx_userId_roleId` (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ϵͳ-�û���ɫ��';


CREATE TABLE `sys_menu` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '�˵�����',
  `title` varchar(64) NOT NULL DEFAULT '' COMMENT '����',
  `icon` varchar(255) NOT NULL DEFAULT '' COMMENT 'ͼ��',
  `path` varchar(255) NOT NULL DEFAULT '' COMMENT '·��',
  `component` varchar(255) NOT NULL DEFAULT '' COMMENT 'ǰ�����',
  `root_level` bit(1) NOT NULL DEFAULT b'0' COMMENT '�����˵�',
  `parent_id` varchar(32) NOT NULL DEFAULT '' COMMENT '���˵�ID',
  `access` int(11) NOT NULL DEFAULT '0' COMMENT '����Ȩ��ֵ',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '��������',
  `create_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `create_by` varchar(32) NOT NULL DEFAULT '' COMMENT '������',
  `update_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `update_by` varchar(32) NOT NULL DEFAULT '' COMMENT '������',
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT 'ɾ����־��0-������1-��ɾ����',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ϵͳ-�˵���';

