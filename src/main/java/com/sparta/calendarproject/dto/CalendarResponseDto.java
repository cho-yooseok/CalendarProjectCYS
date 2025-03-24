package com.sparta.calendarproject.dto;

import com.sparta.calendarproject.model.CalendarModel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CalendarResponseDto {
    private Long id;
    private String todolist;
    private String author;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    // 모델을 DTO로 변환하는 정적 메서드
    public static CalendarResponseDto fromModel(CalendarModel model) {
        CalendarResponseDto dto = new CalendarResponseDto();
        dto.setId(model.getId());
        dto.setTodolist(model.getTodolist());
        dto.setAuthor(model.getAuthor());
        dto.setCreateDate(model.getCreateDate());
        dto.setUpdateDate(model.getUpdateDate());
        return dto;
    }
}