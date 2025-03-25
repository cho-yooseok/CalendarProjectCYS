package com.sparta.calendarproject.controller;

import com.sparta.calendarproject.dto.CalendarRequestDto;
import com.sparta.calendarproject.dto.CalendarResponseDto;
import com.sparta.calendarproject.service.CalendarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 컨트롤러 클래스: 웹 요청을 처리하고 서비스 계층과 상호작용하는 역할
@RestController // REST API 엔드포인트를 제공하는 컨트롤러임을 나타냄
@RequestMapping("/api") // 모든 메서드의 기본 URL 경로를 "/api"로 설정
public class CalendarController {

    // 캘린더 서비스를 다루는 비즈니스 로직을 호출하기 위한 서비스 객체
    private final CalendarService calendarService;

    // 생성자: CalendarService 객체를 주입받아 초기화
    // 의존성 주입(Dependency Injection)을 통해 서비스 객체를 받음
    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    // 새로운 일정을 생성하는 메서드
    @PostMapping("/calendar") // HTTP POST 요청을 처리하는 엔드포인트
    public ResponseEntity<CalendarResponseDto> createCalendar(@RequestBody CalendarRequestDto requestDto) {
        // 클라이언트로부터 받은 일정 데이터(requestDto)로 새 일정 생성
        // HTTP 상태 코드 201(Created)과 함께 생성된 일정 반환
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(calendarService.createCalendar(requestDto));
    }

    // 모든 일정을 조회하는 메서드
    @GetMapping("/lists") // HTTP GET 요청을 처리하는 엔드포인트
    public ResponseEntity<List<CalendarResponseDto>> getAllCalendars(
            @RequestParam(required = false) String author, // 작성자로 필터링 (옵션)
            @RequestParam(required = false) String updateDate) { // 업데이트 날짜로 필터링 (옵션)
        // 전체 일정 목록을 조회 (필요에 따라 작성자나 업데이트 날짜로 필터링)
        // HTTP 상태 코드 200(OK)과 함께 일정 목록 반환
        return ResponseEntity.ok(calendarService.getAllCalendars(author, updateDate));
    }

    // 특정 ID의 일정을 조회하는 메서드
    @GetMapping("/lists/{id}") // URL 경로에서 ID를 받는 GET 요청 엔드포인트
    public ResponseEntity<CalendarResponseDto> getCalendarById(@PathVariable Long id) {
        // 주어진 ID로 특정 일정 조회
        // HTTP 상태 코드 200(OK)과 함께 해당 일정 반환
        return ResponseEntity.ok(calendarService.getCalendarById(id));
    }

    // 기존 일정을 수정하는 메서드
    @PutMapping("/{id}") // HTTP PUT 요청을 처리하는 엔드포인트
    public ResponseEntity<?> updateCalendar(
            @PathVariable Long id, // URL에서 수정할 일정의 ID
            @RequestBody CalendarRequestDto requestDto) { // 수정할 데이터
        try {
            // 일정 수정 시도
            CalendarResponseDto updatedCalendar = calendarService.updateCalendar(id, requestDto);
            // 성공 시 수정된 일정과 함께 HTTP 200(OK) 상태 반환
            return ResponseEntity.ok().body(updatedCalendar);
        } catch (IllegalArgumentException e) {
            // 수정 중 오류 발생 시 (예: 잘못된 비밀번호) 400 Bad Request 반환
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 일정을 삭제하는 메서드
    @DeleteMapping("/{id}") // HTTP DELETE 요청을 처리하는 엔드포인트
    public ResponseEntity<?> deleteCalendar(
            @PathVariable Long id, // URL에서 삭제할 일정의 ID
            @RequestParam String password) { // 삭제 확인을 위한 비밀번호
        try {
            // 일정 삭제 시도
            calendarService.deleteCalendar(id, password);
            // 성공 시 삭제 메시지와 함께 HTTP 200(OK) 상태 반환
            return ResponseEntity.ok().body("삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            // 삭제 중 오류 발생 시 (예: 잘못된 비밀번호) 400 Bad Request 반환
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}



/*
주요설명과 공부한 내용정리

이 컨트롤러는 캘린더 애플리케이션의 REST API 엔드포인트를 정의하고 있습니다.

중요 특징은 다음과 같다
1. 기본적인 CRUD(Create,Read,Update,Delete) 작업을 처리한다
2. 각 메서드는 특정 HTTP메서드(POST,GET,PUT,DELETE)와 연결되어 있습니다
3. 데이터 유효성 검사와 오류 처리를 포함하고 있습니다
4. 서비스 계층 (CalendarService)과 상호 작용하여 비즈니스 로직을 수행합니다

주요 어노테이션 설명
@RestController : REST API를 제공하는 컨트롤러임을 나타냄
@RequestMapping : 기본 URL 경로 설정
@PostMapping : 리소스 생성 요청 처리
@GetMapping : 리소스 조회 요청 처리
@PutMapping : 리소스 수정 요청 처리
@DeleteMapping : 리소스 삭제 요청 처리

 */

