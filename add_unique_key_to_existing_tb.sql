# the following two sql queries aim to modify tb_wechat_auth and make the coloum open_id be unique key
# since the table use innodb and charset UTF8MB4. 
# Given the configuration you provided, the open_id column is causing the error because its potential maximum length in bytes (1024 characters * 4 bytes/character = 4096 bytes) exceeds the 3072 byte limit for InnoDB indexes with the utf8mb4 charset.
# so first need to modify the length of open_id then make it unique
ALTER TABLE tb_wechat_auth MODIFY open_id VARCHAR(767);
alter table tb_wechat_auth add unique index open_id(open_id);