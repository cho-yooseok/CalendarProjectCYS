package com.sparta.calendarproject.dto;

import lombok.Getter;
import lombok.Setter;

// Data Transfer Object (DTO): 데이터 전송을 위한 클래스
// 클라이언트로부터 받은 일정 생성/수정 요청 데이터를 담는 객체
@Getter   // Lombok 어노테이션: 모든 필드의 getter 메서드를 자동 생성
@Setter   // Lombok 어노테이션: 모든 필드의 setter 메서드를 자동 생성
public class CalendarRequestDto {
    // 할 일 내용을 저장하는 필드
    // 예: "팀 프로젝트 회의", "운동하기", "책 읽기" 등
    private String todolist;

    // 할 일의 작성자 이름을 저장하는 필드
    // 예: "홍길동", "김개발" 등
    private String author;

    // 일정 수정/삭제 시 보안을 위한 비밀번호 필드
    // 작성자만 해당 일정을 수정하거나 삭제할 수 있도록 보호
    private String password;
}


/*
주요설명과 공부한 내용정리

1. DTO란 무엇인가?
DTO(Data Transfer Object)는 데이터를 전송하는 데 사용되는 객체.
클라이언트(예 : 웹 브라우저)에서 서버로 데이터를 보낼 때 사용됩니다.
이 경우, 새로운 일정을 생성하거나 수정할 때 필요한 정보를 담고 있습니다

2. Lombok 어노테이션의 역할
@Getter : 모든 필드에 대한 getter 메서드를 자동으로 생성합니다.
예) getTodolist(), getAuthor(), getPassword()

@Setter : 모든 필드에 대한 setter 메서드를 자동으로 생성합니다
예) setTodolist(), setAuthor(), setPassword()

3.필드설명
todolist : 할 일의 내용 (문자열)
author : 할 일을 작성한 사람의 이름(문자열)
password : 일정 보호를 위한 비밀번호(문자열)

실제 사용 예시
CalendarRequestDto requestDto = new CalendarRequestDto();
requestDto.setTodolist("팀 프로젝트 회의 준비");
requestDto.setAuthor("김개발");
requestDto.setPassword("1234");

 */