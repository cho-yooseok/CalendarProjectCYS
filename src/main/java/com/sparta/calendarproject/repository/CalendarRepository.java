package com.sparta.calendarproject.repository;

import com.sparta.calendarproject.model.CalendarModel;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class CalendarRepository {

    private final JdbcTemplate jdbcTemplate;

    public CalendarRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 일정 생성
    public CalendarModel createCalendar(CalendarModel calendarModel) {
        String sql = "INSERT INTO calendar (author, todolist, password, createDate, updateDate) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, calendarModel.getAuthor());
            ps.setString(2, calendarModel.getTodolist());
            ps.setString(3, calendarModel.getPassword());
            ps.setObject(4, calendarModel.getCreateDate());
            ps.setObject(5, calendarModel.getUpdateDate());
            return ps;
        }, keyHolder);

        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        calendarModel.setId(id);

        return calendarModel;
    }

    // 전체 일정 조회
    public List<CalendarModel> getAllCalendars(String author, String updateDate) {
        StringBuilder sql = new StringBuilder("SELECT * FROM calendar WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (author != null && !author.isEmpty()) {
            sql.append(" AND author = ?");
            params.add(author);
        }

        if (updateDate != null && !updateDate.isEmpty()) {
            sql.append(" AND DATE(updateDate) = ?");
            params.add(Date.valueOf(updateDate));
        }

        sql.append(" ORDER BY updateDate DESC");

        return jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper<>(CalendarModel.class));
    }

    // 단건 일정 조회
    public CalendarModel getCalendarById(Long id) {
        try {
            String sql = "SELECT * FROM calendar WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(CalendarModel.class), id);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("해당 ID의 일정이 존재하지 않습니다: " + id);
        }
    }

    // 수정
    public void updateCalendar(CalendarModel calendarModel) {
        String sql = "UPDATE calendar SET todolist = ?, author = ?, updateDate = ? WHERE id = ?";
        int affected = jdbcTemplate.update(
                sql,
                calendarModel.getTodolist(),
                calendarModel.getAuthor(),
                calendarModel.getUpdateDate(),
                calendarModel.getId()
        );

        if (affected == 0) {
            throw new IllegalArgumentException("해당 ID의 일정이 존재하지 않습니다: " + calendarModel.getId());
        }
    }

    // 삭제
    public void deleteCalendar(Long id) {
        String sql = "DELETE FROM calendar WHERE id = ?";
        int affected = jdbcTemplate.update(sql, id);

        if (affected == 0) {
            throw new IllegalArgumentException("해당 ID의 일정이 존재하지 않습니다: " + id);
        }
    }

    // 비밀번호 확인
    public String getPasswordById(Long id) {
        try {
            String sql = "SELECT password FROM calendar WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, String.class, id);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("해당 ID의 일정이 존재하지 않습니다: " + id);
        }
    }
}