# hgwzsrping
1. 提前安装 docker 运行环境

brew install docker

2. 安装 mysql 5.7, redis 镜像**

docker pull mysql:5.7
docker pull redis

3. 环境配置准备：**

JDK 1.8
JMeter 5.0+ 版本
Maven
4. 开发工具/知识**

Intellij IDEA
Springboot https://spring.io/projects/spring-boot
Myibatis https://mybatis.org/mybatis-3/zh/index.html
5. 练习工程**

Sprintboot 被测工程：https://github.com/aqie4203/jmeter-springboot-server.git

6. Mysql Create Table

Setup Mysql

docker pull mysql:5.7 mkdir -p ~/mysql/{data,logs} docker run -d --name mysql 
-v ~/mysql/data:/var/lib/mysql 
-e MYSQL_ROOT_PASSWORD=123456 
-p 3306:3306 
mysql:5.7

CREATE DATABASE cityinfo;

DROP TABLE IF EXISTS city; 
CREATE TABLE city ( id int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '城市编号', province_id int(10) unsigned NOT NULL COMMENT '省份编号', city_name varchar(25) DEFAULT NULL COMMENT '城市名称', description varchar(25) DEFAULT NULL COMMENT '描述', PRIMARY KEY (id) ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE INDEX index_name ON city (province_id)

Setup Redis

docker run -d --name redis -p 6379:6379 redis
