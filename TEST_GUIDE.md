# 🍳 WhatToEat API 테스트 가이드

## 📋 개요
WhatToEat 백엔드 API의 카카오 로그인 및 JWT 토큰 기능을 테스트할 수 있는 가이드입니다.

## 🚀 서버 실행
```bash
./gradlew bootRun
```
서버가 실행되면 `http://localhost:8080`에서 접근 가능합니다.

## 🧪 테스트 방법

### 1. 웹 브라우저 테스트 (추천)
브라우저에서 다음 URL로 접속하여 테스트할 수 있습니다:
```
http://localhost:8080/test-login.html
```

**기능:**
- **카카오 OAuth 완전한 플로우**: 인가코드 → 액세스 토큰 → JWT 토큰
- **수동 액세스 토큰 로그인**: 카카오에서 발급받은 액세스 토큰으로 직접 로그인
- **이메일/프로바이더 로그인**: 기존 사용자 로그인
- **회원가입**: 새 사용자 등록
- **JWT 토큰 관리**: 검증, 갱신, 로그아웃
- **API 테스트**: 인증이 필요한 모든 API 테스트

### 2. Postman 테스트
`postman-collection.json` 파일을 Postman에 import하여 사용할 수 있습니다.

**설정 방법:**
1. Postman 실행
2. Import → File → `postman-collection.json` 선택
3. Collection Variables에서 `authToken` 설정

### 3. Swagger UI
API 문서를 확인하고 테스트할 수 있습니다:
```
http://localhost:8080/swagger-ui.html
```

## 🔑 JWT 토큰 확인 방법

### 웹 브라우저에서
1. `http://localhost:8080/test-login.html` 접속
2. 로그인 성공 후 "JWT 토큰 관리" 섹션에서 토큰 확인
3. 토큰이 자동으로 localStorage에 저장됨

### Postman에서
1. 로그인 API 호출 후 응답에서 `token` 필드 복사
2. Collection Variables의 `authToken`에 설정
3. 다른 API 호출 시 자동으로 Authorization 헤더에 포함

## 📝 API 엔드포인트 목록

### 인증 관련
- `POST /accounts/api/kakao/login` - 카카오 액세스 토큰 로그인
- `POST /accounts/api/oauth-login` - 이메일 로그인
- `POST /accounts/api/oauth-register` - 회원가입
- `GET /accounts/api/validate` - 토큰 검증
- `POST /accounts/api/refresh` - 토큰 갱신
- `POST /accounts/api/logout` - 로그아웃

### 재료 관리
- `GET /api/ingredient` - 재료 목록 조회
- `POST /api/ingredient` - 재료 생성
- `PUT /api/ingredient` - 재료 수정

### 레시피 관리
- `POST /api/recipe` - 레시피 생성

### 예산 관리
- `POST /api/budgets` - 예산 생성
- `PUT /api/budgets/{id}` - 예산 수정

### 지출 관리
- `POST /api/expenses` - 지출 생성
- `GET /api/expenses/recents` - 최근 지출 조회

## 🔐 인증 방식

모든 API는 JWT 토큰을 사용한 인증이 필요합니다 (인증 관련 API 제외).

**Authorization 헤더:**
```
Authorization: Bearer {JWT_TOKEN}
```

## 🧪 테스트 시나리오

### 1. 카카오 OAuth 완전한 플로우 테스트
1. **카카오 앱 키 입력**: 카카오 개발자 콘솔에서 발급받은 JavaScript Key 입력
2. **카카오 로그인 시작**: "카카오 로그인 시작" 버튼 클릭
3. **카카오 로그인 페이지**: 새 창에서 카카오 로그인 페이지 열림
4. **카카오 로그인**: 카카오 계정으로 로그인
5. **인가코드 수신**: 자동으로 인가코드를 받아서 액세스 토큰 발급
6. **백엔드 로그인**: 액세스 토큰을 백엔드로 전송하여 JWT 토큰 발급
7. **API 테스트**: JWT 토큰으로 인증이 필요한 API 호출

### 2. 수동 액세스 토큰 테스트
1. **카카오 액세스 토큰 입력**: 카카오에서 발급받은 액세스 토큰 직접 입력
2. **수동 로그인**: "수동 토큰으로 로그인" 버튼 클릭
3. **JWT 토큰 발급**: 백엔드에서 JWT 토큰 발급
4. **API 테스트**: JWT 토큰으로 API 호출

### 3. 기본 로그인 테스트
1. **회원가입**: 이메일, 프로바이더, 닉네임으로 회원가입
2. **JWT 토큰 확인**: 응답에서 JWT 토큰 확인
3. **토큰 검증**: 토큰 유효성 검증
4. **API 테스트**: 인증이 필요한 API 호출

### 4. 토큰 갱신 테스트
1. **로그인**: 어떤 방식으로든 로그인
2. **토큰 저장**: JWT 토큰이 localStorage에 저장됨
3. **토큰 갱신**: "토큰 갱신" 버튼 클릭
4. **새 토큰 확인**: 새로운 JWT 토큰 발급 확인
5. **API 테스트**: 새 토큰으로 API 호출

## 🐛 문제 해결

### CORS 오류
- 브라우저에서 테스트 시 CORS 오류가 발생할 수 있습니다.
- Postman이나 서버에서 직접 테스트하는 것을 권장합니다.

### 토큰 만료
- JWT 토큰은 기본적으로 1시간 후 만료됩니다.
- 토큰 갱신 API를 사용하여 새로운 토큰을 발급받으세요.

### 인증 실패
- Authorization 헤더가 올바르게 설정되었는지 확인
- 토큰이 유효한지 토큰 검증 API로 확인

## 📊 응답 예시

### 로그인 성공
```json
{
  "status": "success",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "message": "로그인 성공"
}
```

### 토큰 검증 성공
```json
{
  "valid": true,
  "message": "유효한 토큰입니다."
}
```

### API 호출 성공 (재료 목록)
```json
[
  {
    "id": 1,
    "name": "양파",
    "quantity": 2,
    "unit": "개",
    "exprirationDate": "2024-02-15T00:00:00.000+00:00"
  }
]
```

## 🎯 다음 단계

1. 프론트엔드와 연동
2. 실제 카카오 로그인 구현
3. 프로덕션 환경 설정
4. 보안 강화 (HTTPS, 토큰 암호화 등)
