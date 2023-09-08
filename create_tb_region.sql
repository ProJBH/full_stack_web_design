use o2o;
create table `tb_region`(
`region_id` int(2) NOT NULL AUTO_INCREMENT,
`region_name` varchar(200) NOT NULL,
`priority` int(2) NOT NULL DEFAULT '0',
`create_time` datetime DEFAULT NULL,
`modified_time` datetime DEFAULT NULL,
primary key(`region_id`),
unique key `UK_REGION`(`region_name`) 
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET= UTF8MB4;

