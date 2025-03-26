
# 캘린더 프로젝트

## 소개
이 프로젝트는 일정 관리 애플리케이션입니다. 사용자는 일정을 생성, 조회, 수정, 삭제할 수 있습니다. Spring Boot를 사용하여 RESTful API로 구현되었으며, 데이터베이스와 연동하여 일정을 저장합니다.

## ERD
![calendar_erd_cys.png](src/main/resources/calendar_erd_cys.png)


## API 명세서 링크
https://documenter.getpostman.com/view/42104801/2sAYkKJdYw



## 주요 기능
- **일정 생성**: 사용자가 새로운 일정을 생성할 수 있습니다.
- **일정 조회**: 모든 일정을 조회하거나 특정 작성자 또는 수정 날짜로 필터링하여 조회할 수 있습니다.
- **일정 수정**: 사용자는 비밀번호를 통해 기존 일정을 수정할 수 있습니다.
- **일정 삭제**: 사용자는 비밀번호를 통해 기존 일정을 삭제할 수 있습니다.

 기술 스택
- 
-
- 
- 
- 

## 프로젝트 구조
```markdown
calendar-project/
├── src/
│   ├── main/
│      ├── java/
│         └── com/
│             └── sparta/
│                 └── calendarproject/
│                     ├── controller/   # 웹 요청을 처리하는 컨트롤러
│                     ├── dto/          # 데이터 전송 객체 (DTO)
│                     ├── model/        # 데이터베이스 모델
│                     ├── repository/   # 데이터베이스 접근 계층
│                     └── service/      # 비즈니스 로직 처리 계층
│   
└── README.md  
└── img.png #ERD
└── schedule.sql #테이블 생성에 필요한 쿼리문

```