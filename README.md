# VYBZ Comment Service

VYBZ 플랫폼의 댓글 기능을 담당하는 마이크로서비스입니다.

## 📋 목차

-   [개요](#개요)
-   [기술 스택](#기술-스택)
-   [주요 기능](#주요-기능)
-   [프로젝트 구조](#프로젝트-구조)
-   [API 문서](#api-문서)
-   [설치 및 실행](#설치-및-실행)
-   [환경 설정](#환경-설정)
-   [댓글 시스템](#댓글-시스템)
-   [이벤트 처리](#이벤트-처리)

## 🎯 개요

VYBZ Comment Service는 다음과 같은 기능을 제공합니다:

-   **댓글 작성**: 피드/공지 등에 댓글 작성
-   **댓글 수정**: 작성한 댓글 수정
-   **댓글 삭제**: 작성한 댓글 삭제
-   **대댓글 지원**: 부모 댓글에 대한 대댓글 작성
-   **이벤트 처리**: Kafka를 통한 댓글 이벤트 발행
-   **데이터 저장**: MongoDB를 통한 댓글 데이터 저장
-   **서비스 디스커버리**: Eureka Client를 통한 서비스 등록

## 🛠 기술 스택

### Backend

![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white)
![Apache Kafka](https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)

### Infra

![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)
![Amazon EC2](https://img.shields.io/badge/Amazon_EC2-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

### 협업

![Discord](https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=discord&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)

### Database & Cache

-   **MongoDB**: 댓글 데이터 저장 및 관리

### Message Queue

-   **Apache Kafka**: 비동기 이벤트 처리

### Documentation

-   **Swagger/OpenAPI 3.0**: API 문서화

### Build & Deploy

-   **Gradle**: 빌드 도구
-   **Docker**: 컨테이너화

## 🚀 주요 기능

### 1. 댓글 관리 시스템

-   **댓글 작성**: 피드/공지 등에 댓글 작성
-   **댓글 수정**: 작성한 댓글 수정 (작성자만 가능)
-   **댓글 삭제**: 작성한 댓글 삭제 (작성자만 가능)
-   **대댓글 지원**: 부모 댓글에 대한 대댓글 작성
-   **권한 검증**: 댓글 수정/삭제 시 작성자 권한 확인

### 2. 피드 타입 지원

-   **REELS**: 릴스 피드
-   **NOTICE**: 공지사항
-   **ABOUT**: 소개
-   **FAN_FEED**: 팬 피드

### 3. 작성자 타입 지원

-   **USER**: 일반 사용자
-   **BUSKER**: 버스커

### 4. 이벤트 처리

-   **댓글 생성 이벤트**: 댓글 작성 시 이벤트 발행
-   **댓글 수정 이벤트**: 댓글 수정 시 이벤트 발행
-   **댓글 삭제 이벤트**: 댓글 삭제 시 이벤트 발행
-   **댓글 증감 이벤트**: 댓글 수 증감 이벤트 발행

## 📁 프로젝트 구조

```
src/main/java/back/vybz/comment_service/
├── common/                    # 공통 모듈
│   ├── config/               # 설정 클래스들
│   │   ├── MongoConfig.java
│   │   ├── ObjectMapperConfig.java
│   │   └── SwaggerConfig.java
│   ├── entity/               # 공통 엔티티
│   │   ├── BaseResponseEntity.java
│   │   └── BaseResponseStatus.java
│   ├── exception/            # 예외 처리
│   │   ├── AsyncExceptionHandler.java
│   │   ├── BaseException.java
│   │   ├── BaseExceptionHandler.java
│   │   ├── BaseExceptionHandlerFilter.java
│   │   └── BaseResponseStatus.java
│   └── util/                 # 유틸리티
│       └── CursorPage.java
├── kafka/                    # Kafka 이벤트 처리
│   ├── config/               # Kafka 설정
│   │   ├── CommentCreateEventKafkaConfig.java
│   │   ├── CommentDeleteEventKafkaConfig.java
│   │   ├── CommentDeltaEventKafkaConfig.java
│   │   ├── CommentUpdateEventKafkaConfig.java
│   │   └── CommonKafkaProducerConfig.java
│   ├── event/                # 이벤트 모델
│   │   ├── CommentCreateEvent.java
│   │   ├── CommentDeleteEvent.java
│   │   ├── CommentDeltaEvent.java
│   │   └── CommentUpdateEvent.java
│   └── producer/             # 이벤트 프로듀서
│       ├── CommentCreateEventKafkaProducer.java
│       ├── CommentDeleteEventKafkaProducer.java
│       ├── CommentDeltaEventKafkaProducer.java
│       └── CommentUpdateEventKafkaProducer.java
├── comment/                  # 댓글 도메인
│   ├── application/          # 댓글 서비스 로직
│   │   ├── CommentService.java
│   │   └── CommentServiceImpl.java
│   ├── domain/               # 댓글 도메인 모델
│   │   ├── mongodb/
│   │   │   ├── Comment.java
│   │   │   ├── FeedType.java
│   │   │   └── WriterType.java
│   ├── dto/                  # 댓글 DTO
│   │   ├── request/
│   │   │   ├── RequestAddCommentDto.java
│   │   │   └── RequestUpdateCommentDto.java
│   │   └── response/
│   │       └── ResponseAddCommentDto.java
│   ├── infrastructure/       # 댓글 리포지토리
│   │   ├── CommentRepository.java
│   │   ├── CommentRepositoryCustom.java
│   │   └── CommentRepositoryCustomImpl.java
│   ├── presentation/         # 댓글 컨트롤러
│   │   └── CommentController.java
│   └── vo/                   # 댓글 VO
│       ├── request/
│       │   ├── RequestAddCommentVo.java
│       │   └── RequestUpdateCommentVo.java
└── CommentServiceApplication.java
```

## 📚 API 문서

Swagger UI를 통해 API 문서를 확인할 수 있습니다:

-   **URL**: `http://localhost:8000/comment-service/swagger-ui/index.html`
-   **API 그룹**: COMMENT-SERVICE

### 주요 API 엔드포인트

#### 댓글 관리 API

-   `POST /api/v1/comment` - 댓글 작성
-   `PUT /api/v1/comment/{commentId}` - 댓글 수정
-   `DELETE /api/v1/comment/{commentId}` - 댓글 삭제

### API 요청/응답 예시

#### 댓글 작성 요청

```json
{
    "feedId": "feed-uuid-123",
    "feedType": "REELS",
    "writerUuid": "user-uuid-456",
    "writerType": "USER",
    "comment": "정말 멋진 릴스네요!",
    "parentCommentId": null
}
```

#### 댓글 수정 요청

```json
{
    "writerUuid": "user-uuid-456",
    "comment": "수정된 댓글 내용입니다."
}
```

## 🚀 설치 및 실행

### 1. 사전 요구사항

-   Java 17
-   Gradle 8.4+
-   Docker (선택사항)
-   MongoDB 6.0+
-   Kafka 3.0+

### 2. 로컬 실행

```bash
# 프로젝트 클론
git clone <repository-url>
cd vybz-comment

# Gradle 빌드
./gradlew clean build

# 애플리케이션 실행
./gradlew bootRun
```

### 3. Docker 실행

```bash
# Docker 이미지 빌드
docker build -t vybz-comment .

# Docker 컨테이너 실행
docker run -p 8000:8000 vybz-comment
```

## ⚙️ 환경 설정

### 주요 설정 파일

-   `application.yml`: 기본 설정

### 환경 변수

```yaml
# MongoDB 설정
spring:
  data:
    mongodb:
      uri: mongodb://${MONGO_USERNAME}:${MONGO_PASSWORD}@${MONGO_HOST}:${MONGO_PORT}/${MONGO_DATABASE}?authSource=admin&replicaSet=myReplicaSet

  kafka:
    bootstrap-servers: ${KAFKA_SERVERS}
```

## 💬 댓글 시스템

#### 피드 타입 (FeedType)

-   **REELS**: 릴스 피드
-   **NOTICE**: 공지사항
-   **ABOUT**: 소개
-   **FAN_FEED**: 팬 피드

#### 작성자 타입 (WriterType)

-   **USER**: 일반 사용자
-   **BUSKER**: 버스커

### 데이터베이스 설계

#### 커스텀 쿼리

```java
// 댓글 수정을 위한 커스텀 쿼리
public void updateCommentById(String id, RequestUpdateCommentDto requestUpdateCommentDto) {
    Query query = new Query(Criteria.where("_id").is(id)
                                    .and("writerUuid").is(requestUpdateCommentDto.getWriterUuid()));
    Update update = new Update();
    if (requestUpdateCommentDto.getComment() != null) {
        update.set("comment", requestUpdateCommentDto.getComment());
    }
    update.set("updatedAt", Instant.now());

    mongoTemplate.updateFirst(query, update, Comment.class);
}
```

## 📡 이벤트 처리

### Kafka 이벤트

#### 발행 이벤트

-   **CommentCreateEvent**: 댓글 생성 이벤트

    -   `id`: 댓글 ID
    -   `feedId`: 피드 ID
    -   `feedType`: 피드 타입
    -   `writerUuid`: 작성자 UUID
    -   `writerType`: 작성자 타입
    -   `comment`: 댓글 내용
    -   `parentCommentId`: 부모 댓글 ID
    -   `createdAt`: 생성 시간

-   **CommentUpdateEvent**: 댓글 수정 이벤트

    -   `id`: 댓글 ID
    -   `feedId`: 피드 ID
    -   `feedType`: 피드 타입
    -   `writerUuid`: 작성자 UUID
    -   `writerType`: 작성자 타입
    -   `comment`: 수정된 댓글 내용
    -   `parentCommentId`: 부모 댓글 ID
    -   `updatedAt`: 수정 시간

-   **CommentDeleteEvent**: 댓글 삭제 이벤트

    -   `commentId`: 댓글 ID
    -   `feedId`: 피드 ID

-   **CommentDeltaEvent**: 댓글 증감 이벤트
    -   `feedId`: 피드 ID
    -   `commentId`: 댓글 ID
    -   `feedType`: 피드 타입
    -   `delta`: 증감 값 (+1: 증가, -1: 감소)

### 이벤트 프로듀서

-   `CommentCreateEventKafkaProducer`: 댓글 생성 이벤트 발행
-   `CommentUpdateEventKafkaProducer`: 댓글 수정 이벤트 발행
-   `CommentDeleteEventKafkaProducer`: 댓글 삭제 이벤트 발행
-   `CommentDeltaEventKafkaProducer`: 댓글 증감 이벤트 발행

### Kafka 토픽

-   `comment-create-event`: 댓글 생성 이벤트 토픽
-   `comment-update-event`: 댓글 수정 이벤트 토픽
-   `comment-delete-event`: 댓글 삭제 이벤트 토픽
-   `comment-delta-event`: 댓글 증감 이벤트 토픽

### 이벤트 발행 시점

-   **댓글 생성**: 사용자가 댓글 작성 시
-   **댓글 수정**: 사용자가 댓글 수정 시
-   **댓글 삭제**: 사용자가 댓글 삭제 시

## 🏗 아키텍처

### 도메인 주도 설계 (DDD)

-   **Domain Layer**: 댓글 도메인 모델과 비즈니스 로직
-   **Application Layer**: 댓글 서비스 로직과 유스케이스
-   **Infrastructure Layer**: 데이터베이스 접근과 외부 시스템 연동
-   **Presentation Layer**: REST API 엔드포인트

### 마이크로서비스 패턴

-   **Service Discovery**: Eureka Client를 통한 서비스 등록
-   **Event-Driven**: Kafka를 통한 비동기 이벤트 처리
-   **Stateless**: 상태 없는 서비스 설계

### 데이터베이스 설계

-   **MongoDB**: 댓글 데이터 저장 및 관리

### 이벤트 기반 아키텍처

-   **이벤트 발행**: 댓글 CRUD 작업 시 이벤트 발행
-   **비동기 처리**: Kafka를 통한 비동기 이벤트 처리
-   **이벤트 소싱**: 댓글 상태 변경 이력 추적

## 🔧 개발 가이드

### 코드 컨벤션

-   **패키지 구조**: 도메인별 계층 분리
-   **네이밍**: 명확하고 일관된 네이밍 규칙
-   **예외 처리**: BaseException을 통한 통일된 예외 처리
-   **로깅**: Slf4j를 통한 구조화된 로깅

### 테스트

```bash
# 단위 테스트 실행
./gradlew test

# 통합 테스트 실행
./gradlew integrationTest
```

### 예외 처리

```java
// 댓글 관련 예외
public enum BaseResponseStatus {
    COMMENT_NOT_FOUND("댓글을 찾을 수 없습니다."),
    COMMENT_CREATE_FAIL("댓글 생성에 실패했습니다."),
    NO_EXIST_COMMENT_OR_NO_AUTH("댓글이 존재하지 않거나 권한이 없습니다.");
}
```

### 성능 최적화

#### 데이터베이스 최적화

-   **인덱싱**: 자주 조회되는 필드에 인덱스 설정
-   **배치 처리**: 대량 데이터 처리 시 배치 사용
-   **커스텀 쿼리**: 복잡한 쿼리는 MongoTemplate 사용

#### Kafka 최적화

-   **배치 전송**: 메시지 배치 처리
-   **파티션 관리**: 토픽 파티션 최적화
-   **컨슈머 그룹**: 컨슈머 그룹 설정

## 📊 모니터링

### 로깅

-   **애플리케이션 로그**: Spring Boot 로깅
-   **댓글 로그**: 댓글 CRUD 작업 로깅
-   **Kafka 로그**: 이벤트 발행 로깅
-   **데이터베이스 로그**: 쿼리 성능 로깅

### 메트릭

-   **댓글 처리량**: 초당 처리 댓글 수
-   **응답 시간**: API 응답 시간
-   **에러율**: 에러 발생률
-   **Kafka 메시지**: 이벤트 발행 성공률

### 알림

-   **댓글 생성 실패**: 댓글 생성 실패 알림
-   **데이터베이스 오류**: DB 연결 오류 알림
-   **Kafka 오류**: 메시지 전송 실패 알림

## 🚨 트러블슈팅

### 일반적인 문제

#### MongoDB 연결 실패

```bash
# MongoDB 연결 확인
mongo mongodb://vybz:<비밀번호>@<탄력적 IP>:27020/vybz?authSource=admin&replicaSet=myReplicaSet

# 복제본 세트 상태 확인
rs.status()
```

#### Kafka 연결 실패

```bash
# Kafka 브로커 상태 확인
kafka-topics.sh --bootstrap-server <탄력적 IP>:10000 --list

# 토픽 상세 정보 확인
kafka-topics.sh --bootstrap-server <탄력적 IP>:10000 --describe --topic comment-create-event
```

#### Eureka 연결 실패

```bash
# Eureka 서버 상태 확인
curl http://eureka:8761/eureka/apps/comment-service

# 서비스 등록 확인
curl http://eureka:8761/eureka/apps
```

### 로그 확인

```bash
# 애플리케이션 로그 확인
tail -f logs/application.log

# 에러 로그 확인
grep "ERROR" logs/application.log

# Kafka 로그 확인
grep "Kafka" logs/application.log
```

## 📝 라이선스

이 프로젝트는 VYBZ 팀의 내부 프로젝트입니다.

## 👥 팀

-   **개발팀**: VYBZ Backend Team

---

**VYBZ Comment Service** - MongoDB 기반 댓글 관리 서비스
