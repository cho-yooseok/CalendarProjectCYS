package com.sparta.calendarproject.service;

import com.sparta.calendarproject.dto.CalendarRequestDto;
import com.sparta.calendarproject.dto.CalendarResponseDto;
import com.sparta.calendarproject.model.CalendarModel;
import com.sparta.calendarproject.repository.CalendarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalendarService {

    private final CalendarRepository calendarRepository;

    public CalendarService(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    // 일정 생성
    public CalendarResponseDto createCalendar(CalendarRequestDto requestDto) {
        CalendarModel model = CalendarModel.from(requestDto);
        CalendarModel savedModel = calendarRepository.createCalendar(model);
        return CalendarResponseDto.fromModel(savedModel);
    }

    // 전체 일정 조회
    public List<CalendarResponseDto> getAllCalendars(String author, String updateDate) {
        return calendarRepository.getAllCalendars(author, updateDate)
                .stream()
                .map(CalendarResponseDto::fromModel)
                .collect(Collectors.toList());
    }

    // 단건 일정 조회
    public CalendarResponseDto getCalendarById(Long id) {
        return CalendarResponseDto.fromModel(calendarRepository.getCalendarById(id));
    }

    // 수정
    public CalendarResponseDto updateCalendar(Long id, CalendarRequestDto requestDto) {
        // 비밀번호 확인
        String storedPassword = calendarRepository.getPasswordById(id);
        if (!storedPassword.equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 일정 가져오기
        CalendarModel model = calendarRepository.getCalendarById(id);

        // 일정 업데이트
        model.update(requestDto);

        // 저장
        calendarRepository.updateCalendar(model);

        return CalendarResponseDto.fromModel(model);
    }

    // 삭제
    public void deleteCalendar(Long id, String password) {
        // 비밀번호 확인
        String storedPassword = calendarRepository.getPasswordById(id);
        if (!storedPassword.equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        calendarRepository.deleteCalendar(id);
    }
}