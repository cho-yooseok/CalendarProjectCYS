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

-- 공부한 내용 정리

-- 데이터베이스 생성 명령어
-- 'calendar' 라는 이름의 데이터베이스를 만든다
-- UTF-8 문자 인코딩을 사용하여 다국어 지원 및 특수 문자 처리를 가능하게 합니다
-- dafault character set utf8 : 문자 인코딩을 UTF-8로 설정
-- collate utf8_general_ci : 문자 정렬 및 비교 규칙을 지정 (대소문자 구분 없는 정렬)
-- create database calendar default character set utf8 collate utf8_general_ci;


-- 방금 생성한 'calendar'데이터베이스를 사용하겠다고 선언
-- 이후의 모든 SQL 명령어는 이 데이터베이스에서 실행됨
-- use calendar;


-- 'calendar' 테이블 생성
-- 할 일 목록(todolist)을 저장하기 위한 테이블
-- create table calendar (



-- id : 각 항목을 고유하게 식별하는 기본 키
-- auto_increment : 새 레코드 추가 시 자동으로 1씩 증가
-- primary key : 테이블의 기본 키로 지정 (중복 불가, 고유 식별자)
-- id int auto_increment primary key,



-- varchar(100) : 최대 100자 까지의 가변 길이 문자열
-- not null : 반드시 값이 있어야 함(NULL 값 허용 안함)  , 필수 입력 필드
-- timestamp : 날짜와 시간 저장 , 자동 타임 스탬프 : 생성 및 수정 시간을 자동으로 기록



