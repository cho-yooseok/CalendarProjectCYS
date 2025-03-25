package com.sparta.calendarproject.service;

import com.sparta.calendarproject.dto.CalendarRequestDto;
import com.sparta.calendarproject.dto.CalendarResponseDto;
import com.sparta.calendarproject.model.CalendarModel;
import com.sparta.calendarproject.repository.CalendarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// 비즈니스 로직을 처리하는 서비스 계층
// 컨트롤러와 레포지토리 사이의 중간 계층
@Service  // Spring이 서비스 컴포넌트로 인식하게 하는 어노테이션
public class CalendarService {

    // 데이터베이스 접근을 위한 레포지토리 객체
    private final CalendarRepository calendarRepository;

    // 생성자를 통한 의존성 주입
    // CalendarRepository 객체를 외부에서 받아 초기화
    public CalendarService(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    // 새로운 일정을 생성하는 메서드
    public CalendarResponseDto createCalendar(CalendarRequestDto requestDto) {
        // 1. RequestDto를 Model로 변환
        CalendarModel model = CalendarModel.from(requestDto);

        // 2. 레포지토리를 통해 데이터베이스에 저장
        CalendarModel savedModel = calendarRepository.createCalendar(model);

        // 3. 저장된 Model을 ResponseDto로 변환하여 반환
        return CalendarResponseDto.fromModel(savedModel);
    }

    // 모든 일정을 조회하는 메서드
    // 작성자와 업데이트 날짜로 필터링 가능
    public List<CalendarResponseDto> getAllCalendars(String author, String updateDate) {
        // 1. 레포지토리에서 일정 목록 조회
        // 2. 각 Model을 ResponseDto로 변환
        // 3. 변환된 목록을 List로 수집
        return calendarRepository.getAllCalendars(author, updateDate)
                .stream()
                .map(CalendarResponseDto::fromModel)
                .collect(Collectors.toList());
    }

    // 특정 ID의 일정을 조회하는 메서드
    public CalendarResponseDto getCalendarById(Long id) {
        // 1. 레포지토리에서 일정 조회
        // 2. Model을 ResponseDto로 변환하여 반환
        return CalendarResponseDto.fromModel(calendarRepository.getCalendarById(id));
    }

    // 일정을 수정하는 메서드
    public CalendarResponseDto updateCalendar(Long id, CalendarRequestDto requestDto) {
        // 1. 저장된 비밀번호와 입력된 비밀번호 비교
        String storedPassword = calendarRepository.getPasswordById(id);
        if (!storedPassword.equals(requestDto.getPassword())) {
            // 비밀번호가 일치하지 않으면 예외 발생
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 2. 수정할 일정 조회
        CalendarModel model = calendarRepository.getCalendarById(id);

        // 3. 일정 내용 업데이트
        model.update(requestDto);

        // 4. 업데이트된 일정 저장
        calendarRepository.updateCalendar(model);

        // 5. 업데이트된 일정을 ResponseDto로 변환하여 반환
        return CalendarResponseDto.fromModel(model);
    }

    // 일정을 삭제하는 메서드
    public void deleteCalendar(Long id, String password) {
        // 1. 저장된 비밀번호와 입력된 비밀번호 비교
        String storedPassword = calendarRepository.getPasswordById(id);
        if (!storedPassword.equals(password)) {
            // 비밀번호가 일치하지 않으면 예외 발생
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 2. 일정 삭제
        calendarRepository.deleteCalendar(id);
    }
}


/*
주요설명과 공부한 내용정리

1. 서비스 계층의 역할
비즈니스 로직을 처리하는 중간 계층
컨트롤러(요청 처리)와 레포지토리(데이터 접근) 사이에 위치
데이터 변환, 검증, 비즈니스 규칙 적용 담당

2. 주요 메서드 흐름
createCalendar :
요청 데이터 -> 모델변환 -> 데이터베이스 저장 -> 응답 데이터 반환

getAllCalendars :
데이터베이스 조회 -> 모델을 응답데이터로 변환

getCalendarById :
특정 ID의 일정 조회

updateCalendar :
비밀번호 검증
일정 조회
내용 업데이트
데이터베이스 저장

deleteCalendar :
비밀번호 검증
일정 삭제

3. 주요 기능
데이터 변환 (RequestDto <-> Model <-> ResponseDto)
보안검증 (비밀번호 확인)
예외처리

4.StreamAPI 사용
stream() : 컬렉션을 스트림으로 변환
map() : 각 요소를 다른 형태로 변환
collect() : 스트림의 요소를 컬렉션으로 수집

5. 의존성 주입
생성자를 통해 CalendarRepository 주입
느슨한 결합(LooseCoupling) 유지

주의할 점
모든 메서드에서 데이터변환과 보안검증 수행
명확한 비즈니스 로직 분리
예외 상황(비밀번호 불일치 등)에 대한 처리

이 서비스는 애플리케이션의 핵심 비즈니스 로직을
안전하고 명확하게 관리함
 */