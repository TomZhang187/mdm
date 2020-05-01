
-- ----------------------------
-- Table structure for alipay_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_alipay_config`;
CREATE TABLE `sys_alipay_config`  (
  `id` SERIAL NOT NULL,
  `app_id` varchar(255),
  `charset` varchar(255),
  `format` varchar(255),
  `gateway_url` varchar(255),
  `notify_url` varchar(255),
  `private_key` text,
  `public_key` text,
  `return_url` varchar(255),
  `sign_type` varchar(255),
  `sys_service_provider_id` varchar(255),
  PRIMARY KEY (`id`) USING BTREE
)

-- ----------------------------
-- Records of alipay_config
-- ----------------------------
INSERT INTO `sys_alipay_config` VALUES (1, '2016091700532697', 'utf-8', 'JSON', 'https://openapi.alipaydev.com/gateway.do', 'http://api.auauz.net/api/aliPay/notify', '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', 'http://api.auauz.net/api/aliPay/return', 'RSA2', '2088102176044281');

-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept`  (
  `id` serial NOT NULL,
  `name` varchar(255) ,
  `pid` int8(20) ,
  `create_time` timestamp DEFAULT NULL,
  `enabled` boolean NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ;

-- ----------------------------
-- Records of dept
-- ----------------------------

INSERT INTO dept (id,name, pid, create_time, enabled) VALUES (2, '研发部', 7, '2019-03-25 09:15:32', TRUE);
INSERT INTO dept (id,name, pid, create_time, enabled)  VALUES (5, '运维部', 7, '2019-03-25 09:20:44', TRUE);
INSERT INTO dept (id,name, pid, create_time, enabled)  VALUES (6, '测试部', 8, '2019-03-25 09:52:18', TRUE);
INSERT INTO dept (id,name, pid, create_time, enabled) VALUES (7, '华南分部', 1, '2019-03-25 11:04:50', TRUE);
INSERT INTO dept (id,name, pid, create_time, enabled)  VALUES (8, '华北分部', 1, '2019-03-25 11:04:53', TRUE);
INSERT INTO dept (id,name, pid, create_time, enabled)  VALUES (9, '财务部', 7, '2019-03-25 11:05:34', TRUE);
INSERT INTO dept (id,name, pid, create_time, enabled)  VALUES (10, '行政部', 8, '2019-03-25 11:05:58', TRUE);
INSERT INTO dept (id,name, pid, create_time, enabled)  VALUES (11, '人事部', 8, '2019-03-25 11:07:58', TRUE);
INSERT INTO dept (id,name, pid, create_time, enabled)  VALUES (12, '市场部', 7, '2019-03-25 11:10:24', TRUE);
-- ----------------------------
-- Table structure for dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` serial NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典名称',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
);

-- ----------------------------
-- Records of dict
-- ----------------------------
INSERT INTO sys_dict(id,name,remark) VALUES (1, 'user_status', '用户状态');
INSERT INTO sys_dict(id,name,remark) VALUES (4, 'dept_status', '部门状态');
INSERT INTO sys_dict(id,name,remark) VALUES (5, 'job_status', '岗位状态');
INSERT INTO sys_dict(id,name,remark) VALUES (6, 'tt', 'tt');

-- ----------------------------
-- Table structure for dict_detail
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_detail`;
CREATE TABLE `sys_dict_detail`  (
  `id` serial not null,
  `label` varchar(255),
  `value` varchar(255),
  `sort` varchar(255),
  `dict_id` bigint(11),
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK5tpkputc6d9nboxojdbgnpmyb`(`dict_id`) USING BTREE,
  CONSTRAINT `FK5tpkputc6d9nboxojdbgnpmyb` FOREIGN KEY (`dict_id`) REFERENCES `dict` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

-- ----------------------------
-- Records of dict_detail
-- ----------------------------
INSERT INTO sys_dict_detail(id,label,value,sort,dict_id) VALUES (1, '激活', 'true', '1', 1);
INSERT INTO sys_dict_detail(id,label,value,sort,dict_id) VALUES (2, '锁定', 'false', '2', 1);
INSERT INTO sys_dict_detail(id,label,value,sort,dict_id) VALUES (11, '正常', 'true', '1', 4);
INSERT INTO sys_dict_detail(id,label,value,sort,dict_id) VALUES (12, '停用', 'false', '2', 4);
INSERT INTO sys_dict_detail(id,label,value,sort,dict_id) VALUES (13, '正常', 'true', '1', 5);
INSERT INTO sys_dict_detail(id,label,value,sort,dict_id) VALUES (14, '停用', 'false', '2', 5);
INSERT INTO sys_dict_detail(id,label,value,sort,dict_id) VALUES (15, 'f', 'fdaf', '1', 6);

-- ----------------------------
-- Table structure for email_config
-- ----------------------------
DROP TABLE IF EXISTS `email_config`;
CREATE TABLE `email_config`  (
  `id` serial NOT NULL ,
  `from_user` varchar(255),
  `host` varchar(255),
  `pass` varchar(255),
  `port` varchar(255),
  `user` varchar(255),
  PRIMARY KEY (`id`) USING BTREE
);

-- ----------------------------
-- Table structure for gen_config
-- ----------------------------
DROP TABLE IF EXISTS `gen_config`;
CREATE TABLE `gen_config`  (
  `id` serial NOT NULL,
  `author` varchar(255),
  `cover` boolean DEFAULT NULL COMMENT '是否覆盖',
  `module_name` varchar(255)  DEFAULT NULL COMMENT '模块名称',
  `pack` varchar(255) DEFAULT NULL COMMENT '至于哪个包下',
  `path` varchar(255) DEFAULT NULL COMMENT '前端代码生成的路径',
  `api_path` varchar(255) DEFAULT NULL,
  `prefix` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
);

-- ----------------------------
-- Records of gen_config
-- ----------------------------
INSERT INTO gen_config(id,author,cover,module_name,pack,path,api_path,prefix) VALUES (1, 'Zheng Jie', b'0', 'eladmin-tools', 'me.zhengjie', 'E:\\workspace\\me\\front\\eladmin-qt\\src\\views\\tools\\storage\\local', 'E:\\workspace\\me\\front\\eladmin-qt\\src\\api', NULL);

-- ----------------------------
-- Table structure for job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`  (
  `id` serial,
  `name` varchar(255),
  `enabled` boolean NOT NULL,
  `create_time` timestamp DEFAULT NULL,
  `sort` bigint(20) NOT NULL,
  `dept_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKmvhj0rogastlctflsxf1d6k3i`(`dept_id`) USING BTREE,
  CONSTRAINT `FKmvhj0rogastlctflsxf1d6k3i` FOREIGN KEY (`dept_id`) REFERENCES `dept` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

-- ----------------------------
-- Records of job
-- ----------------------------
INSERT INTO sys_job(id,name,enabled,create_time,sort,dept_id) VALUES (2, '董事长秘书', True, '2019-03-29 14:01:30', 2, 1);
 INSERT INTO sys_job(id,name,enabled,create_time,sort,dept_id) VALUES (8, '人事专员', True, '2019-03-29 14:52:28', 3, 11);
 INSERT INTO sys_job(id,name,enabled,create_time,sort,dept_id) VALUES (10, '产品经理', False, '2019-03-29 14:55:51', 4, 2);
 INSERT INTO sys_job(id,name,enabled,create_time,sort,dept_id) VALUES (11, '全栈开发', True, '2019-03-31 13:39:30', 6, 2);
 INSERT INTO sys_job(id,name,enabled,create_time,sort,dept_id) VALUES (12, '软件测试', True, '2019-03-31 13:39:43', 5, 2);
 INSERT INTO sys_job(id,name,enabled,create_time,sort,dept_id) VALUES (19, '董事长', True, '2019-03-31 14:58:15', 1, 1);

-- ----------------------------
-- Table structure for local_storage
-- ----------------------------
DROP TABLE IF EXISTS `local_storage`;
CREATE TABLE `local_storage`  (
  `id` serial NOT NULL,
  `real_name` varchar(255) NULL DEFAULT NULL,
  `name` varchar(255) NULL DEFAULT NULL COMMENT '文件名',
  `suffix` varchar(255) NULL DEFAULT NULL COMMENT '后缀',
  `path` varchar(255) NULL DEFAULT NULL COMMENT '路径',
  `type` varchar(255) NULL DEFAULT NULL COMMENT '类型',
  `size` varchar(100) NULL DEFAULT NULL COMMENT '大小',
  `operate` varchar(255) NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`) USING BTREE
) ;

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `exception_detail` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `log_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `params` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `request_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time` bigint(20) NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13914 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE sys_menu(
  id serial primary key,
  create_time TIMESTAMP NULL DEFAULT NULL,
  i_frame bit(1) DEFAULT NULL,
  name varchar(255) ,
  component varchar(255) ,
  pid int NOT NULL,
  sort int NOT NULL,
  icon varchar(255) ,
  path varchar(255) ,
  cache bit(1) NULL DEFAULT '0',
  hidden bit(1) NULL DEFAULT '0',
  component_name varchar(20) NULL DEFAULT '-'
);

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (1, '2018-12-18 15:11:29', '0', '系统管理', NULL, 0, 1, 'system', 'system', false, false, NULL);
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (2, '2018-12-18 15:14:44', '0', '用户管理', 'system/user/index', 1, 2, 'peoples', 'user', false, false, 'User');
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (3, '2018-12-18 15:16:07', '0', '角色管理', 'system/role/index', 1, 3, 'role', 'role', false, false, 'Role');
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (4, '2018-12-18 15:16:45', '0', '权限管理', 'system/permission/index', 1, 4, 'permission', 'permission', false, false, 'Permission');
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (5, '2018-12-18 15:17:28', '0', '菜单管理', 'system/menu/index', 1, 5, 'menu', 'menu',false, false, 'Menu');
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (6, '2018-12-18 15:17:48', '0', '系统监控', NULL, 0, 10, 'monitor', 'monitor', false, false, NULL);
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (7, '2018-12-18 15:18:26', '0', '操作日志', 'monitor/log/index', 6, 11, 'log', 'logs', True, false, 'Log');
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (8, '2018-12-18 15:19:01', '0', '系统缓存', 'monitor/redis/index', 6, 13, 'redis', 'redis', false, false, 'Redis');
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (9, '2018-12-18 15:19:34', '0', 'SQL监控', 'monitor/sql/index', 6, 14, 'sqlMonitor', 'druid', false, false, 'Sql');
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (10, '2018-12-19 13:38:16', '0', '组件管理', NULL, 0, 50, 'zujian', 'components', false, false, NULL);
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (11, '2018-12-19 13:38:49', '0', '图标库', 'components/IconSelect', 10, 51, 'icon', 'icon', false, false, 'Icons');
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (14, '2018-12-27 10:13:09', '0', '邮件工具', 'tools/email/index', 36, 24, 'email', 'email',false, false, 'Email');
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (15, '2018-12-27 11:58:25', '0', '富文本', 'components/Editor', 10, 52, 'fwb', 'tinymce', false, false, 'Editor');
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (16, '2018-12-28 09:36:53', '0', '图床管理', 'tools/picture/index', 36, 25, 'image', 'pictures', false, false, 'Pictures');
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (17, '2018-12-28 15:09:49', '1', '项目地址', '', 0, 0, 'github', 'https://github.com/elunez/eladmin', false,false, NULL);
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (18, '2018-12-31 11:12:15', '0', '存储管理', 'tools/storage/index', 36, 23, 'qiniu', 'storage', false, false, 'Storage');
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (19, '2018-12-31 14:52:38', '0', '支付宝工具', 'tools/aliPay/index', 36, 27, 'alipay', 'aliPay', false, false, 'AliPay');
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (21, '2019-01-04 16:22:03', '0', '多级菜单', '', 0, 900, 'menu', 'nested', False, False, NULL);
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (22, '2019-01-04 16:23:29', '0', '二级菜单1', 'nested/menu1/index', 21, 999, 'menu', 'menu1', False, False, NULL);
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (23, '2019-01-04 16:23:57', '0', '二级菜单2', 'nested/menu2/index', 21, 999, 'menu', 'menu2', False, False, NULL);
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (24, '2019-01-04 16:24:48', '0', '三级菜单1', 'nested/menu1/menu1-1', 22, 999, 'menu', 'menu1-1', False, False, NULL);
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (27, '2019-01-07 17:27:32', '0', '三级菜单2', 'nested/menu1/menu1-2', 22, 999, 'menu', 'menu1-2', False, False, NULL);
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (28, '2019-01-07 20:34:40', '0', '定时任务', 'system/timing/index', 36, 21, 'timing', 'timing', False, False, 'Timing');
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (30, '2019-01-11 15:45:55', '0', '代码生成', 'generator/index', 36, 22, 'dev', 'generator', False, False, 'GeneratorIndex');
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (32, '2019-01-13 13:49:03', '0', '异常日志', 'monitor/log/errorLog', 6, 12, 'error', 'errorLog', False, False, 'ErrorLog');
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (33, '2019-03-08 13:46:44', '0', 'Markdown', 'components/MarkDown', 10, 53, 'markdown', 'markdown', False, False, 'Markdown');
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (34, '2019-03-08 15:49:40', '0', 'Yaml编辑器', 'components/YamlEdit', 10, 54, 'dev', 'yaml', False, False, 'YamlEdit');
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (35, '2019-03-25 09:46:00', '0', '部门管理', 'system/dept/index', 1, 6, 'dept', 'dept', False, False, 'Dept');
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (36, '2019-03-29 10:57:35', '0', '系统工具', '', 0, 20, 'sys-tools', 'sys-tools', False, False, NULL);
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (37, '2019-03-29 13:51:18', '0', '岗位管理', 'system/job/index', 1, 7, 'Steve-Jobs', 'job', False, False, 'Job');
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (38, '2019-03-29 19:57:53', '0', '接口文档', 'tools/swagger/index', 36, 26, 'swagger', 'swagger2', False, False, 'Swagger');
INSERT INTO sys_menu(id,create_time,i_frame,name,component,pid,sort,icon,path,cache,hidden,component_name) VALUES (39, '2019-04-10 11:49:04', '0', '字典管理', 'system/dict/index', 1, 8, 'dictionary', 'dict', False, False, 'Dict');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` serial NOT NULL COMMENT 'ID',
  `alias` varchar(255) DEFAULT NULL COMMENT '别名',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建日期',
  `name` varchar(255) NULL DEFAULT NULL COMMENT '名称',
  `pid` int(11) NOT NULL COMMENT '上级权限',
  PRIMARY KEY (`id`) USING BTREE
);

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (1, '超级管理员', '2018-12-03 12:27:48', 'ADMIN', 0);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (2, '用户管理', '2018-12-03 12:28:19', 'USER_ALL', 0);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (3, '用户查询', '2018-12-03 12:31:35', 'USER_SELECT', 2);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (4, '用户创建', '2018-12-03 12:31:35', 'USER_CREATE', 2);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (5, '用户编辑', '2018-12-03 12:31:35', 'USER_EDIT', 2);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (6, '用户删除', '2018-12-03 12:31:35', 'USER_DELETE', 2);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (7, '角色管理', '2018-12-03 12:28:19', 'ROLES_ALL', 0);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (8, '角色查询', '2018-12-03 12:31:35', 'ROLES_SELECT', 7);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (10, '角色创建', '2018-12-09 20:10:16', 'ROLES_CREATE', 7);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (11, '角色编辑', '2018-12-09 20:10:42', 'ROLES_EDIT', 7);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (12, '角色删除', '2018-12-09 20:11:07', 'ROLES_DELETE', 7);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (13, '权限管理', '2018-12-09 20:11:37', 'PERMISSION_ALL', 0);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (14, '权限查询', '2018-12-09 20:11:55', 'PERMISSION_SELECT', 13);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (15, '权限创建', '2018-12-09 20:14:10', 'PERMISSION_CREATE', 13);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (16, '权限编辑', '2018-12-09 20:15:44', 'PERMISSION_EDIT', 13);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (17, '权限删除', '2018-12-09 20:15:59', 'PERMISSION_DELETE', 13);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (18, '缓存管理', '2018-12-17 13:53:25', 'REDIS_ALL', 0);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (20, '缓存查询', '2018-12-17 13:54:07', 'REDIS_SELECT', 18);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (22, '缓存删除', '2018-12-17 13:55:04', 'REDIS_DELETE', 18);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (23, '图床管理', '2018-12-27 20:31:49', 'PICTURE_ALL', 0);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (24, '查询图片', '2018-12-27 20:32:04', 'PICTURE_SELECT', 23);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (25, '上传图片', '2018-12-27 20:32:24', 'PICTURE_UPLOAD', 23);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (26, '删除图片', '2018-12-27 20:32:45', 'PICTURE_DELETE', 23);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (29, '菜单管理', '2018-12-28 17:34:31', 'MENU_ALL', 0);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (30, '菜单查询', '2018-12-28 17:34:41', 'MENU_SELECT', 29);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (31, '菜单创建', '2018-12-28 17:34:52', 'MENU_CREATE', 29);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (32, '菜单编辑', '2018-12-28 17:35:20', 'MENU_EDIT', 29);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (33, '菜单删除', '2018-12-28 17:35:29', 'MENU_DELETE', 29);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (35, '定时任务管理', '2019-01-08 14:59:57', 'JOB_ALL', 0);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (36, '任务查询', '2019-01-08 15:00:09', 'JOB_SELECT', 35);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (37, '任务创建', '2019-01-08 15:00:20', 'JOB_CREATE', 35);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (38, '任务编辑', '2019-01-08 15:00:33', 'JOB_EDIT', 35);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (39, '任务删除', '2019-01-08 15:01:13', 'JOB_DELETE', 35);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (40, '部门管理', '2019-03-29 17:06:55', 'DEPT_ALL', 0);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (41, '部门查询', '2019-03-29 17:07:09', 'DEPT_SELECT', 40);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (42, '部门创建', '2019-03-29 17:07:29', 'DEPT_CREATE', 40);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (43, '部门编辑', '2019-03-29 17:07:52', 'DEPT_EDIT', 40);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (44, '部门删除', '2019-03-29 17:08:14', 'DEPT_DELETE', 40);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (45, '岗位管理', '2019-03-29 17:08:52', 'USERJOB_ALL', 0);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (46, '岗位查询', '2019-03-29 17:10:27', 'USERJOB_SELECT', 45);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (47, '岗位创建', '2019-03-29 17:10:55', 'USERJOB_CREATE', 45);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (48, '岗位编辑', '2019-03-29 17:11:08', 'USERJOB_EDIT', 45);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (49, '岗位删除', '2019-03-29 17:11:19', 'USERJOB_DELETE', 45);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (50, '字典管理', '2019-04-10 16:24:51', 'DICT_ALL', 0);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (51, '字典查询', '2019-04-10 16:25:16', 'DICT_SELECT', 50);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (52, '字典创建', '2019-04-10 16:25:29', 'DICT_CREATE', 50);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (53, '字典编辑', '2019-04-10 16:27:19', 'DICT_EDIT', 50);
INSERT INTO sys_permission(id,alias,create_time,name,pid) VALUES (54, '字典删除', '2019-04-10 16:27:30', 'DICT_DELETE', 50);

-- ----------------------------
-- Table structure for picture
-- ----------------------------
DROP TABLE IF EXISTS `picture`;
CREATE TABLE `picture`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '上传日期',
  `delete_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除的URL',
  `filename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片名称',
  `height` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片高度',
  `size` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片大小',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `width` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片宽度',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qiniu_config
-- ----------------------------
-- DROP TABLE IF EXISTS `qiniu_config`;
-- CREATE TABLE `qiniu_config`  (
--   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
--   `access_key` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'accessKey',
--   `bucket` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Bucket 识别符',
--   `host` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '外链域名',
--   `secret_key` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'secretKey',
--   `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '空间类型',
--   `zone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机房',
--   PRIMARY KEY (`id`) USING BTREE
-- ) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
--
-- -- ----------------------------
-- -- Table structure for qiniu_content
-- -- ----------------------------
-- DROP TABLE IF EXISTS `qiniu_content`;
-- CREATE TABLE `qiniu_content`  (
--   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
--   `bucket` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Bucket 识别符',
--   `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名称',
--   `suffix` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件后缀',
--   `size` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件大小',
--   `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件类型：私有或公开',
--   `update_time` datetime NULL DEFAULT NULL COMMENT '上传或同步的时间',
--   `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件url',
--   PRIMARY KEY (`id`) USING BTREE
-- ) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
--
-- -- ----------------------------
-- -- Table structure for quartz_job
-- -- ----------------------------
-- DROP TABLE IF EXISTS `quartz_job`;
-- CREATE TABLE `quartz_job`  (
--   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
--   `bean_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Spring Bean名称',
--   `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'cron 表达式',
--   `is_pause` bit(1) NULL DEFAULT NULL COMMENT '状态：1暂停、0启用',
--   `job_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务名称',
--   `method_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方法名称',
--   `params` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数',
--   `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
--   `update_time` datetime NULL DEFAULT NULL COMMENT '创建或更新日期',
--   PRIMARY KEY (`id`) USING BTREE
-- ) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
--
-- -- ----------------------------
-- -- Records of quartz_job
-- -- ----------------------------
INSERT INTO quartz_job(id,bean_name,cron_expression,is_pause,job_name,method_name,params,remark,update_time) VALUES (1, 'visitsTask', '0 0 0 * * ?', '0', '更新访客记录', 'run', NULL, '每日0点创建新的访客记录', '2019-01-08 14:53:31');
INSERT INTO quartz_job(id,bean_name,cron_expression,is_pause,job_name,method_name,params,remark,update_time) VALUES (2, 'testTask', '0/5 * * * * ?', '1', '测试1', 'run1', 'test', '带参测试，多参使用json', '2019-08-22 14:08:29');
INSERT INTO quartz_job(id,bean_name,cron_expression,is_pause,job_name,method_name,params,remark,update_time) VALUES (3, 'testTask', '0/5 * * * * ?', '1', '测试', 'run', '', '不带参测试', '2019-08-22 14:08:28');
--
-- -- ----------------------------
-- -- Table structure for quartz_log
-- -- ----------------------------
-- DROP TABLE IF EXISTS `quartz_log`;
-- CREATE TABLE `quartz_log`  (
--   `id` bigint(20) NOT NULL AUTO_INCREMENT,
--   `baen_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
--   `create_time` datetime NULL DEFAULT NULL,
--   `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
--   `exception_detail` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
--   `is_success` bit(1) NULL DEFAULT NULL,
--   `job_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
--   `method_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
--   `params` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
--   `time` bigint(20) NULL DEFAULT NULL,
--   PRIMARY KEY (`id`) USING BTREE
-- ) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS role;
CREATE TABLE role  (
  id serial NOT NULL,
  create_time timestamp NULL DEFAULT NULL COMMENT '创建日期',
  name varchar(255) NOT NULL COMMENT '名称',
  remark varchar(255)  NULL DEFAULT NULL COMMENT '备注',
  data_scope varchar(255) NULL DEFAULT NULL,
  level int(255) NULL DEFAULT NULL,
  PRIMARY KEY (id) USING BTREE
);

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO role(id,create_time,name,remark,data_scope,level) VALUES (1, '2018-11-23 11:04:37', '超级管理员', '·', '全部', 1);
INSERT INTO role(id,create_time,name,remark,data_scope,level) VALUES (2, '2018-11-23 13:09:06', '普通用户', '用于测试菜单与权限', '自定义', 3);
INSERT INTO role(id,create_time,name,remark,data_scope,level) VALUES (4, '2019-05-13 14:16:15', '普通管理员', '普通管理员级别为2，使用该角色新增用户时只能赋予比普通管理员级别低的角色', '自定义', 2);

-- ----------------------------
-- Table structure for roles_depts
-- ----------------------------
DROP TABLE IF EXISTS `roles_depts`;
CREATE TABLE `roles_depts`  (
  role_id serial NOT NULL,
  dept_id serial NOT NULL,
  PRIMARY KEY (role_id, dept_id) USING BTREE,
  INDEX 'FK7qg6itn5ajdoa9h9o78v9ksur'(dept_id) USING BTREE,
  CONSTRAINT `FK7qg6itn5ajdoa9h9o78v9ksur` FOREIGN KEY (`dept_id`) REFERENCES `dept` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKrg1ci4cxxfbja0sb0pddju7k` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ;

-- ----------------------------
-- Records of roles_depts
-- ----------------------------
INSERT INTO roles_depts(role_id,dept_id) VALUES (2, 5);
INSERT INTO roles_depts(role_id,dept_id) VALUES (4, 6);
INSERT INTO roles_depts(role_id,dept_id) VALUES (4, 7);
INSERT INTO roles_depts(role_id,dept_id) VALUES (2, 8);
INSERT INTO roles_depts(role_id,dept_id) VALUES (2, 9);

-- ----------------------------
-- Table structure for roles_menus
-- ----------------------------
DROP TABLE IF EXISTS `roles_menus`;
CREATE TABLE `roles_menus`  (
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`menu_id`, `role_id`) USING BTREE,
  INDEX `FKcngg2qadojhi3a651a5adkvbq`(`role_id`) USING BTREE,
  CONSTRAINT `FKo7wsmlrrxb2osfaoavp46rv2r` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKtag324maketmxffly3pdyh193` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of roles_menus
-- ----------------------------
INSERT INTO roles_menus(menu_id, role_id) VALUES (1, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (2, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (3, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (4, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (5, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (6, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (7, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (8, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (9, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (10, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (11, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (14, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (15, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (16, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (17, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (18, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (19, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (21, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (22, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (23, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (24, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (27, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (28, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (30, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (32, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (33, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (34, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (35, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (36, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (37, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (38, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (39, 1);
INSERT INTO roles_menus(menu_id, role_id) VALUES (1, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (2, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (3, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (4, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (5, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (6, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (8, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (9, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (10, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (11, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (14, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (15, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (16, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (17, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (18, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (19, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (21, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (22, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (23, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (24, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (27, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (28, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (30, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (33, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (34, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (35, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (36, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (37, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (38, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (39, 2);
INSERT INTO roles_menus(menu_id, role_id) VALUES (1, 4);
INSERT INTO roles_menus(menu_id, role_id) VALUES (2, 4);

-- ----------------------------
-- Table structure for roles_permissions
-- ----------------------------
DROP TABLE IF EXISTS `roles_permissions`;
CREATE TABLE `roles_permissions`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `permission_id` bigint(20) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`role_id`, `permission_id`) USING BTREE,
  INDEX `FKboeuhl31go7wer3bpy6so7exi`(`permission_id`) USING BTREE,
  CONSTRAINT `FK4hrolwj4ned5i7qe8kyiaak6m` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKboeuhl31go7wer3bpy6so7exi` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ;

-- ----------------------------
-- Records of roles_permissions
-- ----------------------------
INSERT INTO roles_permissions VALUES (1, 1);
INSERT INTO roles_permissions VALUES (2, 3);
INSERT INTO roles_permissions VALUES (4, 3);
INSERT INTO roles_permissions VALUES (4, 4);
INSERT INTO roles_permissions VALUES (4, 5);
INSERT INTO roles_permissions VALUES (2, 8);
INSERT INTO roles_permissions VALUES (2, 14);
INSERT INTO roles_permissions VALUES (2, 20);
INSERT INTO roles_permissions VALUES (2, 23);
INSERT INTO roles_permissions VALUES (2, 24);
INSERT INTO roles_permissions VALUES (2, 25);
INSERT INTO roles_permissions VALUES (2, 26);
INSERT INTO roles_permissions VALUES (2, 30);
INSERT INTO roles_permissions VALUES (2, 36);
INSERT INTO roles_permissions VALUES (2, 41);
INSERT INTO roles_permissions VALUES (2, 46);
INSERT INTO roles_permissions VALUES (2, 51);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `avatar_id` bigint(20) NULL DEFAULT NULL COMMENT '头像',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `enabled` bigint(20) NULL DEFAULT NULL COMMENT '状态：1启用、0禁用',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `last_password_reset_time` datetime NULL DEFAULT NULL COMMENT '最后修改密码的日期',
  `dept_id` bigint(20) NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `job_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_kpubos9gc2cvtkb0thktkbkes`(`email`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  INDEX `FK5rwmryny6jthaaxkogownknqp`(`dept_id`) USING BTREE,
  INDEX `FKfftoc2abhot8f2wu6cl9a5iky`(`job_id`) USING BTREE,
  INDEX `FKpq2dhypk2qgt68nauh2by22jb`(`avatar_id`) USING BTREE,
  CONSTRAINT `FK5rwmryny6jthaaxkogownknqp` FOREIGN KEY (`dept_id`) REFERENCES `dept` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKfftoc2abhot8f2wu6cl9a5iky` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKpq2dhypk2qgt68nauh2by22jb` FOREIGN KEY (`avatar_id`) REFERENCES `user_avatar` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO user(id,avatar_id,create_time,email,enabled,password,username,last_password_reset_time,dept_id,phone,job_id) VALUES (1, NULL, '2018-08-23 09:11:56', 'admin@eladmin.net', true, 'e10adc3949ba59abbe56e057f20f883e', 'admin', '2019-05-18 17:34:21', 2, '18888888888', 11);
INSERT INTO user(id,avatar_id,create_time,email,enabled,password,username,last_password_reset_time,dept_id,phone,job_id) VALUES (3, NULL, '2018-12-27 20:05:26', 'test@eladmin.net', true, 'e10adc3949ba59abbe56e057f20f883e', 'test', '2019-04-01 09:15:24', 2, '17777777777', 12);
INSERT INTO user(id,avatar_id,create_time,email,enabled,password,username,last_password_reset_time,dept_id,phone,job_id) VALUES (5, NULL, '2019-04-02 10:07:12', 'hr@eladmin.net', true, 'e10adc3949ba59abbe56e057f20f883e', 'hr', NULL, 11, '15555555555', 8);

-- ----------------------------
-- Table structure for user_avatar
-- ----------------------------
DROP TABLE IF EXISTS `user_avatar`;
CREATE TABLE `user_avatar`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `real_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `size` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for users_roles
-- ----------------------------
DROP TABLE IF EXISTS `users_roles`;
CREATE TABLE `users_roles`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `FKq4eq273l04bpu4efj0jd0jb98`(`role_id`) USING BTREE,
  CONSTRAINT `FKgd3iendaoyh04b95ykqise6qh` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKt4v0rrweyk393bdgt107vdx0x` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of users_roles
-- ----------------------------
INSERT INTO `users_roles` VALUES (1, 1);
INSERT INTO `users_roles` VALUES (3, 2);
INSERT INTO `users_roles` VALUES (5, 4);

-- ----------------------------
-- Table structure for verification_code
-- ----------------------------
DROP TABLE IF EXISTS `verification_code`;
CREATE TABLE `verification_code`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '验证码',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `status` bit(1) NULL DEFAULT NULL COMMENT '状态：1有效、0过期',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '验证码类型：email或者短信',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接收邮箱或者手机号码',
  `scenes` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务名称：如重置邮箱、重置密码等',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for visits
-- ----------------------------
DROP TABLE IF EXISTS `visits`;
CREATE TABLE `visits`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT NULL,
  `date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ip_counts` bigint(20) NULL DEFAULT NULL,
  `pv_counts` bigint(20) NULL DEFAULT NULL,
  `week_day` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_11aksgq87euk9bcyeesfs4vtp`(`date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 89 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
