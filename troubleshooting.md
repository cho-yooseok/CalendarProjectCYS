# 트러블 슈팅

## 문제 상황 1: 자바 숙련도 및 스프링 숙련도가 낮아서 어려움

### 해결방안
- 강의 내용을 복습하고 구글링을 통해 학습하여 숙련도를 올린다.
- 이번 과제를 통해 공부한 내용은 다음과 같다.

### 컨트롤러 클래스 주요 특징
1. 기본적인 CRUD(Create, Read, Update, Delete) 작업을 처리한다.
2. 각 메서드는 특정 HTTP 메서드(POST, GET, PUT, DELETE)와 연결되어 있다.
3. 데이터 유효성 검사와 오류 처리를 포함한다.
4. 서비스 계층(CalendarService)과 상호 작용하여 비즈니스 로직을 수행한다.

### 주요 어노테이션 설명
- `@RestController` : REST API를 제공하는 컨트롤러임을 나타냄
- `@RequestMapping` : 기본 URL 경로 설정
- `@PostMapping` : 리소스 생성 요청 처리
- `@GetMapping` : 리소스 조회 요청 처리
- `@PutMapping` : 리소스 수정 요청 처리
- `@DeleteMapping` : 리소스 삭제 요청 처리

### DTO란?
DTO(Data Transfer Object)는 데이터를 전송하는 데 사용되는 객체로,
클라이언트(예: 웹 브라우저)에서 서버로 데이터를 보낼 때 사용된다.

### Lombok 어노테이션 역할
- `@Getter` : 모든 필드에 대한 getter 메서드를 자동 생성
- `@Setter` : 모든 필드에 대한 setter 메서드를 자동 생성

### 필드 설명
- `todolist` : 할 일의 내용 (문자열)
- `author` : 할 일을 작성한 사람의 이름 (문자열)
- `password` : 일정 보호를 위한 비밀번호 (문자열)

### DTO 사용 예시
```java
CalendarRequestDto requestDto = new CalendarRequestDto();
requestDto.setTodolist("팀 프로젝트 회의 준비");
requestDto.setAuthor("김개발");
requestDto.setPassword("1234");
```

### `fromModel()` 메서드의 역할
- 데이터베이스 모델(`CalendarModel`)을 응답 DTO로 변환
- 각 필드의 값을 새 객체에 복사하여 클라이언트에 안전하게 전달할 수 있도록 함

### 사용 예시
```java
// 데이터베이스에서 모델 객체를 가져왔다고 가정
CalendarModel model = calendarRepository.findById(1L);

// 모델을 ResponseDto로 변환
CalendarResponseDto responseDto = CalendarResponseDto.fromModel(model);
```

---

## 문제 상황 2: ERD 작성과 API 명세서 작성이 어려움

### 해결방안
- 내일배움캠프 스프링 과정 특별 세션 강의를 복습하여 ERD 작성법과 API 명세서 작성법을 다시 학습
- 구글링을 통해 관련 내용을 추가 학습
- **ERD 설계**: ERD 클라우드를 이용하여 설계
- **API 명세서 작성**: Postman API 명세서를 통해 작성

---

## 문제 상황 3: SQL문 작성이 헷갈려서 오류 발생

### 해결방안
SQL문을 정리하고 복습하여 완전히 숙지할 것.

### 데이터베이스 생성
```sql
CREATE DATABASE calendar DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE calendar;
```

### 테이블 생성
```sql
CREATE TABLE calendar (
    id INT AUTO_INCREMENT PRIMARY KEY,
    author VARCHAR(100) NOT NULL,
    todolist VARCHAR(255) NOT NULL,
    password VARCHAR(100) NOT NULL,
    createDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updateDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### SQL 개념 정리
- `CREATE DATABASE calendar DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;`
    - UTF-8 문자 인코딩 사용 (다국어 지원 및 특수 문자 처리 가능)
    - `collate utf8_general_ci`: 문자 정렬 및 비교 규칙 지정 (대소문자 구분 없음)
- `USE calendar;`
    - 방금 생성한 `calendar` 데이터베이스를 사용하겠다고 선언

---

## 문제 상황 4: 의존성 추가를 깜박하여 오류 발생

### 해결방안
`build.gradle`에 다음 의존성을 추가함
```gradle
implementation 'mysql:mysql-connector-java:8.0.28'
implementation 'org.springframework.boot:spring-boot-starter-data-jdbc'
```

---

## 문제 상황 5: 데이터베이스 연동 시 스키마 부분을 깜박하여 오류 발생

### 해결방안
데이터베이스 연동을 위해 JDBC를 사용하며, `application.properties` 파일에서 설정을 추가해야 함.

### 설정 방법
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/{schema이름}
spring.datasource.username={yourname}
spring.datasource.password={yourpassword}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### 개념 정리
- 여기서 `{schema이름}`은 MySQL에서 **스키마(schema)** 를 의미하며, 이는 데이터베이스와 동일한 개념이다.
- 실제 사용 예시:
    1. MySQL Workbench 또는 CLI에서 `CREATE SCHEMA calendar;` 또는 `CREATE DATABASE calendar;` 실행
    2. `USE calendar;` 명령어를 실행하여 해당 스키마 선택
    3. 이후 테이블을 생성하여 활용

---


