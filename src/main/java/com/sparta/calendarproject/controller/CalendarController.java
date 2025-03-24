package com.sparta.calendarproject.controller;

import com.sparta.calendarproject.dto.CalendarRequestDto;
import com.sparta.calendarproject.dto.CalendarResponseDto;
import com.sparta.calendarproject.service.CalendarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CalendarController {

    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    // 일정 생성
    @PostMapping("/calendar")
    public ResponseEntity<CalendarResponseDto> createCalendar(@RequestBody CalendarRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(calendarService.createCalendar(requestDto));
    }

    // 전체 일정 조회
    @GetMapping("/lists")
    public ResponseEntity<List<CalendarResponseDto>> getAllCalendars(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String updateDate) {
        return ResponseEntity.ok(calendarService.getAllCalendars(author, updateDate));
    }

    // 단건 일정 조회
    @GetMapping("/lists/{id}")
    public ResponseEntity<CalendarResponseDto> getCalendarById(@PathVariable Long id) {
        return ResponseEntity.ok(calendarService.getCalendarById(id));
    }

    // 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCalendar(
            @PathVariable Long id,
            @RequestBody CalendarRequestDto requestDto) {
        try {
            CalendarResponseDto updatedCalendar = calendarService.updateCalendar(id, requestDto);
            return ResponseEntity.ok().body(updatedCalendar);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCalendar(
            @PathVariable Long id,
            @RequestParam String password) {
        try {
            calendarService.deleteCalendar(id, password);
            return ResponseEntity.ok().body("삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}