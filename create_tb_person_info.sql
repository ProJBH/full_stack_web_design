use o2o;
create table `tb_person_info`(
	`user_id` int(10) NOT NULL auto_increment,
    `name` varchar(32) default NULL,
    `profile_img` varchar(1024) default null,
    `email` varchar(1024) default null,
    `gender` varchar(2) default null,
    `enable_status` int(2) not null default '0' comment '0:cannot use this server 1:can use this server',
    `user_type` int(2) not null default '1' comment '1:customer, 2:shop owner,3:super administrator',
    `create_time` datetime default null,
    `last_edit_time` datetime default null,
    primary key(`user_id`)
)engine=InnoDB auto_increment=1 default charset=UTF8MB4;