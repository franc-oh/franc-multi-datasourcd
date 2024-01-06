# 1개의 앱에서 복수의 DB를 연결하게 되는 경우 .. 

## Description

* MariaDB:2 + PostgreSQL:1 연결
* 데이터 일괄등록
  1. Maria_1 + Maria_2의 데이터를 가져와 Postgres에 일괄등록
  2. 엑셀 파일 읽어서 Postgres에 일괄등록

## Environment

* Jdk17
* Spring Boot 3.1.8
* Mybatis 3.0.3
* Gradle 8.4
* log4j2
* MariaDB, PostgreSQL
