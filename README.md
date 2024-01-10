# 1개의 앱에서 복수의 DB를 연결하게 되는 경우 .. 

## 설명
> 2개의 DB로부터 각각 조회된 다량의 데이터를 또다른 DB로 이관하는 프로젝트
- 데이터를 가져올 DB = MariaDB1, MariaDB2
- 데이터가 이관될 DB = PostgresSQL

## 핵심 기능
1. 각 DB별 DataSource를 따로 설정, 여러 DB를 연동할 수 있다.
2. SqlSessionFactory를 통한 대용량 Insert, 다량의 데이터를 빠르게 저장할 수 있다. 
3. log4j2 로깅 (기본 로거 비활성, log4j2 로깅 설정) 

## 개발환경
* Jdk17
* Spring Boot 3.1.8
* Gradle 8.4
* MariaDB, PostgreSQL
* Mybatis

## 특이사항
- '/init_file'에 테이블 스키마 정보 포함