package com.sparta.calendarproject; // 프로젝트의 패키지 경로를 정의합니다.

import org.springframework.boot.SpringApplication; // Spring Boot 애플리케이션을 실행하는 데 필요한 클래스
import org.springframework.boot.autoconfigure.SpringBootApplication; // Spring Boot 애플리케이션 설정을 자동으로 구성해주는 어노테이션

// @SpringBootApplication: 이 어노테이션은 세 가지 중요한 기능을 한 번에 제공합니다:
// 1. @Configuration: 현재 클래스를 스프링 설정 클래스로 표시
// 2. @EnableAutoConfiguration: 스프링 부트의 자동 설정 기능을 활성화
// 3. @ComponentScan: 현재 패키지와 하위 패키지의 컴포넌트들을 자동으로 검색하고 등록
@SpringBootApplication
public class CalendarProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalendarProjectApplication.class, args);
    }
}