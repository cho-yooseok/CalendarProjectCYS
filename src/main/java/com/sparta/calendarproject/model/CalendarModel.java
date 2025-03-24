package com.sparta.calendarproject.model;

import com.sparta.calendarproject.dto.CalendarRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarModel {
    private Long id;
    private String author;
    private String todolist;
    private String password;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    // RequestDto로부터 모델 객체 생성
    public static CalendarModel from(CalendarRequestDto requestDto) {
        CalendarModel model = new CalendarModel();
        model.setAuthor(requestDto.getAuthor());
        model.setTodolist(requestDto.getTodolist());
        model.setPassword(requestDto.getPassword());

        LocalDateTime now = LocalDateTime.now();
        model.setCreateDate(now);
        model.setUpdateDate(now);

        return model;
    }

    // 수정 메서드
    public void update(CalendarRequestDto requestDto) {
        this.todolist = requestDto.getTodolist();
        this.author = requestDto.getAuthor();
        this.updateDate = LocalDateTime.now();
    }
}