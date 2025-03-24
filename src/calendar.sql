-- db 생성
create database calendar default character set utf8 collate utf8_general_ci;

-- db 사용
use calendar;

-- 테이블 생성
create table calendar (
                          id int auto_increment primary key,
                          author varchar(100) not null,
                          todolist varchar(255) not null,
                          password varchar(100) not null,
                          createDate timestamp default current_timestamp,
                          updateDate timestamp default current_timestamp on update current_timestamp
);



-- auto_increment : 새로운 레코드 삽입 시 자동으로 1씩 증가
-- primary key : 해당 컬럼을 기본 키로 지정 (중복 불가, NULL 불가)
-- varchar(100) : 최대 100자 까지의 가변 길이 문자열
-- not null : 반드시 값이 있어야 함(NULL 값 허용 안함)
-- timestamp : 날짜와 시간 저장
