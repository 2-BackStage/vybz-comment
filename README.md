# 🎯 Comment Service

> **댓글 서비스** - 피드 기반 댓글 CRUD 및 실시간 집계 시스템

## 📋 Overview

Comment Service는 피드 기반 댓글 시스템으로, 댓글의 생성/수정/삭제 기능과 함께 실시간 댓글 수 집계를 제공합니다. Kafka를 통한 이벤트 기반 아키텍처로 구성되어 있어 확장성과 성능을 모두 고려한 설계입니다.

### ✨ 주요 기능
- 📝 **댓글 CRUD**: 피드별 댓글 작성, 수정, 삭제
- 🔄 **실시간 집계**: 댓글 수 실시간 업데이트
- 📊 **이벤트 기반**: Kafka를 통한 비동기 처리
- 🗄️ **MongoDB**: NoSQL 기반 데이터 저장
- 🔌 **RESTful API**: 표준 HTTP API 제공

## 🛠 Tech Stack

| 분류 | 기술 | 버전 |
|------|------|------|
| **Language** | Java | 17 |
| **Framework** | Spring Boot | 3.x |
| **Database** | MongoDB | - |
| **Message Queue** | Apache Kafka | - |
| **Build Tool** | Gradle | - |
| **Documentation** | Swagger/OpenAPI | 3.x |

## 📊 서비스 목록

| 서비스명 | 설명 | 언어 | 상태 |
|----------|------|------|------|
| **Comment Service** | 댓글 CRUD 및 이벤트 발행 | Java | ✅ Active |
| **Aggregation Service** | 댓글 수 집계 처리 | Java | 🔄 Planned |
| **Feed Read Service** | 피드 조회 및 댓글 수 표시 | Java | 🔄 Planned |

## 📌 Architecture Diagram

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Client App    │    │   API Gateway   │    │ Comment Service │
│                 │◄──►│                 │◄──►│                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                                       │
                                                       ▼
                                              ┌─────────────────┐
                                              │   MongoDB       │
                                              │                 │
                                              └─────────────────┘
                                                       │
                                                       ▼
                                              ┌─────────────────┐
                                              │   Kafka         │
                                              │                 │
                                              └─────────────────┘
                                                       │
                                                       ▼
                                              ┌─────────────────┐
                                              │ Aggregation     │
                                              │ Service         │
                                              └─────────────────┘
```

## 🚀 Quick Start

### 1. 환경 설정
```bash
# Java 17 설치 확인
java -version

# Gradle 설치 확인
./gradlew --version
```

### 2. 서비스 실행
```bash
# 프로젝트 루트에서 실행
./gradlew bootRun

# 또는 빌드 후 실행
./gradlew build
java -jar build/libs/comment-service-*.jar
```

### 3. API 테스트
```bash
# Swagger UI 접속
http://localhost:8080/swagger-ui.html

# 댓글 작성 예시
curl -X POST "http://localhost:8080/api/v1/comment" \
  -H "Content-Type: application/json" \
  -d '{
    "feedId": "feed123",
    "feedType": "POST",
    "writerUuid": "user123",
    "comment": "테스트 댓글입니다."
  }'
```

## 📁 폴더 구조

```
comment-service/
├── 📄 build.gradle                 # Gradle 빌드 설정
├── 📄 Dockerfile                   # Docker 이미지 설정
├── 📄 README.md                    # 프로젝트 문서
├── 📁 gradle/                      # Gradle Wrapper
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/
│   │   │   └── 📁 back/vybz/comment_service/
│   │   │       ├── 📁 comment/                    # 댓글 도메인
│   │   │       │   ├── 📁 application/            # 애플리케이션 계층
│   │   │       │   │   └── 📁 service/
│   │   │       │   │       ├── CommentService.java
│   │   │       │   │       └── CommentServiceImpl.java
│   │   │       │   ├── 📁 domain/                 # 도메인 계층
│   │   │       │   │   └── 📁 mongodb/
│   │   │       │   │       ├── Comment.java
│   │   │       │   │       ├── FeedType.java
│   │   │       │   │       └── WriterType.java
│   │   │       │   ├── 📁 dto/                    # DTO 계층
│   │   │       │   │   ├── 📁 request/
│   │   │       │   │   │   ├── RequestAddCommentDto.java
│   │   │       │   │   │   └── RequestUpdateCommentDto.java
│   │   │       │   │   └── 📁 response/
│   │   │       │   │       └── ResponseAddCommentDto.java
│   │   │       │   ├── 📁 infrastructure/         # 인프라 계층
│   │   │       │   │   └── 📁 repository/
│   │   │       │   │       ├── CommentRepository.java
│   │   │       │   │       ├── CommentRepositoryCustom.java
│   │   │       │   │       └── CommentRepositoryCustomImpl.java
│   │   │       │   ├── 📁 presentation/           # 프레젠테이션 계층
│   │   │       │   │   └── CommentController.java
│   │   │       │   └── 📁 vo/                     # Value Object
│   │   │       │       └── 📁 request/
│   │   │       │           ├── RequestAddCommentVo.java
│   │   │       │           └── RequestUpdateCommentVo.java
│   │   │       ├── 📁 common/                     # 공통 모듈
│   │   │       │   ├── 📁 config/                 # 설정
│   │   │       │   │   ├── MongoConfig.java
│   │   │       │   │   ├── ObjectMapperConfig.java
│   │   │       │   │   └── SwaggerConfig.java
│   │   │       │   ├── 📁 entity/                 # 공통 엔티티
│   │   │       │   │   ├── BaseResponseEntity.java
│   │   │       │   │   └── BaseResponseStatus.java
│   │   │       │   ├── 📁 exception/              # 예외 처리
│   │   │       │   │   ├── AsyncExceptionHandler.java
│   │   │       │   │   ├── BaseException.java
│   │   │       │   │   ├── BaseExceptionHandler.java
│   │   │       │   │   ├── BaseExceptionHandlerFilter.java
│   │   │       │   │   └── BaseResponseStatus.java
│   │   │       │   └── 📁 util/                   # 유틸리티
│   │   │       │       └── CursorPage.java
│   │   │       ├── 📁 kafka/                      # Kafka 이벤트
│   │   │       │   ├── 📁 config/                 # Kafka 설정
│   │   │       │   │   ├── CommentCreateEventKafkaConfig.java
│   │   │       │   │   ├── CommentDeleteEventKafkaConfig.java
│   │   │       │   │   ├── CommentDeltaEventKafkaConfig.java
│   │   │       │   │   ├── CommentUpdateEventKafkaConfig.java
│   │   │       │   │   └── CommonKafkaProducerConfig.java
│   │   │       │   ├── 📁 event/                  # 이벤트 모델
│   │   │       │   │   ├── CommentCreateEvent.java
│   │   │       │   │   ├── CommentDeleteEvent.java
│   │   │       │   │   ├── CommentDeltaEvent.java
│   │   │       │   │   └── CommentUpdateEvent.java
│   │   │       │   └── 📁 producer/               # 이벤트 발행자
│   │   │       │       ├── CommentCreateEventKafkaProducer.java
│   │   │       │       ├── CommentDeleteEventKafkaProducer.java
│   │   │       │       ├── CommentDeltaEventKafkaProducer.java
│   │   │       │       └── CommentUpdateEventKafkaProducer.java
│   │   │       └── CommentServiceApplication.java # 메인 애플리케이션
│   │   └── 📁 resources/                          # 리소스 파일
│   └── 📁 test/                                   # 테스트 코드
│       └── 📁 java/
│           └── 📁 back/vybz/comment_service/
│               └── CommentServiceApplicationTests.java
```

## 🔧 API Endpoints

### 댓글 관리
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/v1/comment` | 댓글 작성 |
| `PUT` | `/api/v1/comment/{commentId}` | 댓글 수정 |
| `DELETE` | `/api/v1/comment/{commentId}` | 댓글 삭제 |

### 요청 예시
```json
{
  "feedId": "feed123",
  "feedType": "POST",
  "writerUuid": "user123",
  "comment": "테스트 댓글입니다.",
  "parentCommentId": null
}
```

## 📝 개발 가이드

### 코드 컨벤션
- **패키지명**: `back.vybz.comment_service`
- **클래스명**: PascalCase (예: `CommentService`)
- **메서드명**: camelCase (예: `createComment`)
- **변수명**: camelCase (예: `writerUuid`)

### 아키텍처 패턴
- **계층형 아키텍처**: Presentation → Application → Domain → Infrastructure
- **DDD**: Domain-Driven Design 적용
- **Event-Driven**: Kafka를 통한 이벤트 기반 통신

---

<div align="center">

**Made with ❤️ by VYBZ Team**

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green.svg)](https://spring.io/projects/spring-boot)
[![MongoDB](https://img.shields.io/badge/MongoDB-Latest-brightgreen.svg)](https://www.mongodb.com/)
[![Kafka](https://img.shields.io/badge/Apache%20Kafka-Latest-black.svg)](https://kafka.apache.org/)

</div> 