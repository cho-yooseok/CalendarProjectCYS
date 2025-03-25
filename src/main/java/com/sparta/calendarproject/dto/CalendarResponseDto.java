package com.sparta.calendarproject.dto;

import com.sparta.calendarproject.model.CalendarModel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// 응답 데이터 전송 객체 (Data Transfer Object)
// 클라이언트에게 보낼 일정 정보를 담는 클래스
@Getter   // Lombok 어노테이션: 모든 필드의 getter 메서드 자동 생성
@Setter   // Lombok 어노테이션: 모든 필드의 setter 메서드 자동 생성
public class CalendarResponseDto {
    // 일정의 고유 식별자 (데이터베이스 ID)
    // 각 일정을 유일하게 구분하는 번호
    private Long id;

    // 할 일 내용을 저장하는 필드
    // 예: "팀 프로젝트 회의", "운동하기", "책 읽기" 등
    private String todolist;

    // 할 일의 작성자 이름을 저장하는 필드
    // 예: "홍길동", "김개발" 등
    private String author;

    // 일정이 최초로 생성된 날짜와 시간
    // 정확한 생성 시점을 기록
    private LocalDateTime createDate;

    // 일정이 마지막으로 수정된 날짜와 시간
    // 최근 업데이트 시점을 기록
    private LocalDateTime updateDate;

    // 데이터베이스 모델(엔티티)을 응답 DTO로 변환하는 정적 메서드
    // 데이터베이스에서 가져온 모델 객체를 클라이언트에 보낼 수 있는 형태로 변환
    public static CalendarResponseDto fromModel(CalendarModel model) {
        // 새로운 ResponseDto 객체 생성
        CalendarResponseDto dto = new CalendarResponseDto();

        // 모델의 각 필드 값을 DTO의 해당 필드로 복사
        dto.setId(model.getId());           // 고유 ID 설정
        dto.setTodolist(model.getTodolist());  // 할 일 내용 설정
        dto.setAuthor(model.getAuthor());   // 작성자 설정
        dto.setCreateDate(model.getCreateDate());  // 생성 날짜 설정
        dto.setUpdateDate(model.getUpdateDate());  // 수정 날짜 설정

        // 변환된 DTO 객체 반환
        return dto;
    }
}




/*
주요설명과 공부한 내용정리

1. DTO(Data Transfer Object) 란?
데이터를 전송하기 위해 사용되는 특별한 객체입니다
클라이언트(예 : 웹 브라우저, 모바일 앱)에 보낼 데이터를 담고 있습니다
내부 데이터베이스 모델과 외부에 노출되는 데이터를 분리합니다

2.주요 목적
민감한 정보(예 : 비밀번호) 제외
데이터 구조를 클라이언트에 맞게 조정
데이터베이스 모델과 클라이언트 간의 데이터 변환

3.fromModel() 메서드의 역할
데이터베이스모델(CalendarModel)을 응답DTO로 변환
각 필드의 값을 새 객체에 복사
클라이언트에 안전하게 전달할 수 있는 데이터 객체 생성

사용예시
// 데이터베이스에서 모델 객체를 가져왔다고 가정
CalendarModel model = calendarRepository.findById(1L);

// 모델을 ResponseDto로 변환
CalendarResponseDto responseDto = CalendarResponseDto.fromModel(model);

// 이제 responseDto를 클라이언트에 전송가능


주요 특징
id : 각 일정을 고유하게 식별
createDate : 일정 최초 생성 시간
updateDate : 마지막 수정 시간
LocalDateTime : 날짜와 시간을 정확히 기록하는 Java표준 클래스

이 DTO는 데이터베이스 모델의 정보를 안전하고 깔끔하게 클라이언트에 전달하는
역할을 합니다
 */