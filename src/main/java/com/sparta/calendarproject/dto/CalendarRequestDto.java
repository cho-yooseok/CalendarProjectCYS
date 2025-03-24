package com.sparta.calendarproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarRequestDto {
    private String todolist;
    private String author;
    private String password;
}