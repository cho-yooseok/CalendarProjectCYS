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

// 데이터베이스와 직접 상호작용하는 리포지토리 클래스
// 캘린더 관련 데이터베이스 작업을 처리
@Repository  // Spring이 데이터 접근 컴포넌트로 인식하게 하는 어노테이션
public class CalendarRepository {

    // 데이터베이스 작업을 쉽게 수행할 수 있게 해주는 Spring JDBC 템플릿
    private final JdbcTemplate jdbcTemplate;

    // 생성자를 통한 의존성 주입
    // JdbcTemplate 객체를 외부에서 받아 초기화
    public CalendarRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 새로운 일정을 데이터베이스에 생성하는 메서드
    public CalendarModel createCalendar(CalendarModel calendarModel) {
        // INSERT SQL 쿼리: calendar 테이블에 새 일정 추가
        String sql = "INSERT INTO calendar (author, todolist, password, createDate, updateDate) VALUES (?, ?, ?, ?, ?)";

        // 데이터베이스에서 자동 생성된 ID를 받기 위한 키홀더
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // 데이터베이스에 데이터 삽입 및 자동 생성 키 받기
        jdbcTemplate.update(connection -> {
            // PreparedStatement 생성 (SQL 인젝션 방지)
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // 각 파라미터에 값 설정
            ps.setString(1, calendarModel.getAuthor());
            ps.setString(2, calendarModel.getTodolist());
            ps.setString(3, calendarModel.getPassword());
            ps.setObject(4, calendarModel.getCreateDate());
            ps.setObject(5, calendarModel.getUpdateDate());
            return ps;
        }, keyHolder);

        // 데이터베이스에서 자동 생성된 ID 가져오기
        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        calendarModel.setId(id);

        return calendarModel;
    }

    // 전체 일정을 조회하는 메서드
    // 작성자와 업데이트 날짜로 필터링 가능
    public List<CalendarModel> getAllCalendars(String author, String updateDate) {
        // 동적 SQL 쿼리 생성을 위한 StringBuilder
        StringBuilder sql = new StringBuilder("SELECT * FROM calendar WHERE 1=1");
        List<Object> params = new ArrayList<>();

        // 작성자 필터링
        if (author != null && !author.isEmpty()) {
            sql.append(" AND author = ?");
            params.add(author);
        }

        // 업데이트 날짜 필터링
        if (updateDate != null && !updateDate.isEmpty()) {
            sql.append(" AND DATE(updateDate) = ?");
            params.add(Date.valueOf(updateDate));
        }

        // 최근 업데이트 순으로 정렬
        sql.append(" ORDER BY updateDate DESC");

        // 쿼리 실행 및 결과를 CalendarModel 객체 리스트로 반환
        return jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper<>(CalendarModel.class));
    }

    // 특정 ID의 일정을 조회하는 메서드
    public CalendarModel getCalendarById(Long id) {
        try {
            // ID로 일정 조회 SQL
            String sql = "SELECT * FROM calendar WHERE id = ?";
            // 단일 객체 조회 및 반환
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(CalendarModel.class), id);
        } catch (EmptyResultDataAccessException e) {
            // 해당 ID의 일정이 없을 경우 예외 발생
            throw new IllegalArgumentException("해당 ID의 일정이 존재하지 않습니다: " + id);
        }
    }

    // 일정을 수정하는 메서드
    public void updateCalendar(CalendarModel calendarModel) {
        // UPDATE SQL 쿼리
        String sql = "UPDATE calendar SET todolist = ?, author = ?, updateDate = ? WHERE id = ?";

        // 쿼리 실행 및 영향받은 행 수 확인
        int affected = jdbcTemplate.update(
                sql,
                calendarModel.getTodolist(),
                calendarModel.getAuthor(),
                calendarModel.getUpdateDate(),
                calendarModel.getId()
        );

        // 수정된 행이 없으면 해당 ID의 일정이 존재하지 않음
        if (affected == 0) {
            throw new IllegalArgumentException("해당 ID의 일정이 존재하지 않습니다: " + calendarModel.getId());
        }
    }

    // 일정을 삭제하는 메서드
    public void deleteCalendar(Long id) {
        // DELETE SQL 쿼리
        String sql = "DELETE FROM calendar WHERE id = ?";

        // 쿼리 실행 및 영향받은 행 수 확인
        int affected = jdbcTemplate.update(sql, id);

        // 삭제된 행이 없으면 해당 ID의 일정이 존재하지 않음
        if (affected == 0) {
            throw new IllegalArgumentException("해당 ID의 일정이 존재하지 않습니다: " + id);
        }
    }

    // 특정 ID의 일정 비밀번호를 조회하는 메서드
    public String getPasswordById(Long id) {
        try {
            // 비밀번호 조회 SQL
            String sql = "SELECT password FROM calendar WHERE id = ?";
            // 단일 비밀번호 문자열 반환
            return jdbcTemplate.queryForObject(sql, String.class, id);
        } catch (EmptyResultDataAccessException e) {
            // 해당 ID의 일정이 없을 경우 예외 발생
            throw new IllegalArgumentException("해당 ID의 일정이 존재하지 않습니다: " + id);
        }
    }
}


/*
주요설명과 공부한 내용정리

1. 리포지토리(Repository)란?
데이터베이스와 직접 상호작용하는 계층
데이터의 CRUD(Create,Read,Update,Delete) 작업을 담당

2. 주요 기능
일정 생성 (createCalendar)
전체 일정 조회 (getAllCalendars)
단일 일정 조회 (getCalendarById)
일정 수정(updateCalendar)
일정 삭제(deleteCalendar)
비밀번호 확인(getPasswordById)

3.중요 기술 요소
JdbcTemplate : Spring에서 제공하는 데이터베이스 작업 간소화 도구
PreparedStatement : SQL 인젝션 방지를 위한 안전한 쿼리 실행 방식
BeanPropertyRowMapper : 데이터베이스 결과를 Java 객체로 자동 변환

4. 특별한 기능
동적 쿼리 생성 (작성자, 날짜로 필터링 가능)
자동 생성 키(ID) 처리
상세한 예외 처리

주의할 점
모든 데이터베이스 작업에 대해 예외 처리를 제공
존재하지 않는 ID에 대해 명확한 오류 메시지 반환
데이터 보안과 일관성을 고려한 설계

리포지토리는 애플리케이션의 데이터 접근 로직을 안전하고 효율적으로 관리합니다
 */