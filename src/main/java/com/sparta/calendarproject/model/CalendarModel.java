package com.sparta.calendarproject.model;

import com.sparta.calendarproject.dto.CalendarRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// 데이터베이스와 직접 연동되는 엔티티(모델) 클래스
// 캘린더 일정의 실제 데이터 구조를 정의
@Getter   // 모든 필드의 getter 메서드 자동 생성
@Setter   // 모든 필드의 setter 메서드 자동 생성
@NoArgsConstructor  // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 포함하는 생성자 자동 생성
public class CalendarModel {
    // 일정의 고유 식별자 (데이터베이스 기본 키)
    // 각 일정을 유일하게 구분하는 번호
    private Long id;

    // 일정 작성자의 이름
    // 누가 이 일정을 작성했는지 저장
    private String author;

    // 할 일 내용
    // 실제 일정이나 할 일의 상세 내용
    private String todolist;

    // 일정 보호를 위한 비밀번호
    // 수정이나 삭제 시 인증에 사용
    private String password;

    // 일정 최초 생성 시간
    // 언제 이 일정이 처음 만들어졌는지 기록
    private LocalDateTime createDate;

    // 일정 마지막 수정 시간
    // 가장 최근에 수정된 시간 기록
    private LocalDateTime updateDate;

    // RequestDto로부터 모델 객체를 생성하는 정적 팩토리 메서드
    // 클라이언트 요청 데이터를 데이터베이스 모델로 변환
    public static CalendarModel from(CalendarRequestDto requestDto) {
        // 새로운 CalendarModel 객체 생성
        CalendarModel model = new CalendarModel();

        // RequestDto의 데이터를 Model에 복사
        model.setAuthor(requestDto.getAuthor());      // 작성자 설정
        model.setTodolist(requestDto.getTodolist());  // 할 일 내용 설정
        model.setPassword(requestDto.getPassword());  // 비밀번호 설정

        // 현재 시간 가져오기
        LocalDateTime now = LocalDateTime.now();

        // 생성 시간과 수정 시간을 현재 시간으로 설정
        // 처음 생성될 때는 생성 시간과 수정 시간이 같음
        model.setCreateDate(now);
        model.setUpdateDate(now);

        // 생성된 모델 객체 반환
        return model;
    }

    // 일정 정보를 업데이트하는 메서드
    // 할 일 내용과 작성자를 수정하고 수정 시간 갱신
    public void update(CalendarRequestDto requestDto) {
        // 새로운 할 일 내용으로 업데이트
        this.todolist = requestDto.getTodolist();

        // 작성자 정보 업데이트
        this.author = requestDto.getAuthor();

        // 수정 시간을 현재 시간으로 갱신
        this.updateDate = LocalDateTime.now();
    }
}


/*
주요설명과 공부한 내용정리

1.모델(Model)이란?
데이터베이스와 직접 연결되는 데이터 구조를 정의합니다
애플리케이션의 데이터를 표현하고 관리합니다

2.Lombok 어노테이션의 역할
@Getter : 모든 필드의 getter 메서드 자동 생성
@Setter : 모든 필드의 setter 메서드 자동 생성
@NoArgsConstructor : 기본 생성자 자동 생성
@AllArgsConstructor : 모든 필드를 포함하는 생성자 자동 생성

3.주요 메서드 설명
from() 정적 메서드 :
클라이언트 요청(RequestDto)을 데이터베이스 모델로 변환
생성 시간과 수정 시간을 현재 시간으로 설정

update() 메서드 :
기존 일정의 내용을 수정
수정 시간을 현재 시간으로 갱신

// RequestDto로부터 모델 생성
CalendarRequestDto requestDto = new CalendarRequestDto();
requestDto.setTodolist("내일배움캠프 스프링 기초 과제하기");
requestDto.setAuthor("조유석");
requestDto.setPassword("1234");

CalendarModel model = CalendarModel.from(requestDto);

// 모델 업데이트
CalendarRequestDto updateDto = new CalendarRequestDto();
updateDto.setTodolist("내일 배움캠프 공부 준비");
updateDto.setAuthor("조유석");

model.update(updateDto);

주요 특징 :
데이터베이스와 직접 연결되는 클래스
일정의 모든 정보(ID,작성자,내용,비밀번호,생성/수정 시간)를 포함
데이터 변환과 업데이트를 위한 편리한 메서드 제공


이 모델은 애플리케이션의 데이터를 안전하고 체계적으로 관리하는 역할을합니다

 */