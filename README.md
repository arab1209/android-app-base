# Android Clean Architecture Base Template

Jetpack Compose + Hilt + Retrofit 기반 클린 아키텍처 베이스 템플릿

## 프로젝트 구조

```
app/src/main/java/com/example/yourapp/
├── di/                         # 의존성 주입
│   ├── NetworkModule.kt        # Retrofit, OkHttp 설정
│   └── RepositoryModule.kt     # Repository 바인딩
│
├── data/                       # 데이터 레이어
│   ├── remote/                 # 원격 데이터
│   │   ├── api/                # API 인터페이스
│   │   └── datasource/         # Remote DataSource
│   ├── local/                  # 로컬 데이터 (Room 등)
│   ├── mapper/                 # DTO ↔ Entity 변환
│   └── repository/             # Repository 구현체
│
├── domain/                     # 도메인 레이어
│   ├── model/                  # 비즈니스 모델
│   ├── repository/             # Repository 인터페이스
│   └── usecase/                # UseCase
│
└── presentation/               # 프레젠테이션 레이어
    ├── navigation/             # Navigation 설정
    │   ├── AppNavHost.kt
    │   ├── BottomNavItem.kt
    │   └── BottomNavigationBar.kt
    └── ui/                     # 화면별 UI
        ├── main/
        ├── home/
        └── detail/
```

## 레이어 설명

### Domain Layer
비즈니스 로직을 담당하는 레이어. 다른 레이어에 의존하지 않음.

| 패키지 | 역할 |
|--------|------|
| `model` | 비즈니스 모델 (Entity) |
| `repository` | Repository 인터페이스 정의 |
| `usecase` | 단일 비즈니스 로직 수행 |

### Data Layer
데이터 소스와의 통신을 담당하는 레이어.

| 패키지 | 역할 |
|--------|------|
| `remote` | API 통신 (Retrofit) |
| `local` | 로컬 DB (Room) |
| `mapper` | DTO ↔ Entity 변환 |
| `repository` | Repository 구현체 |

### Presentation Layer
UI와 사용자 상호작용을 담당하는 레이어.

| 패키지 | 역할 |
|--------|------|
| `navigation` | 화면 이동 관리 |
| `ui` | Composable 화면, ViewModel |

## 의존성 방향

```
┌─────────────────────────────────────────────────┐
│                  Presentation                    │
│               (UI, ViewModel)                    │
└──────────────────────┬──────────────────────────┘
                       │ 의존
                       ↓
┌─────────────────────────────────────────────────┐
│                    Domain                        │
│       (UseCase, Repository 인터페이스, Model)     │
└──────────────────────┬──────────────────────────┘
                       ↑ 의존
┌──────────────────────┴──────────────────────────┐
│                     Data                         │
│       (RepositoryImpl, DataSource, API, DB)      │
└─────────────────────────────────────────────────┘
```

```
Presentation → Domain ← Data
```

- **Domain**: 중심 레이어, 외부 의존 없음 (순수 Kotlin)
- **Presentation**: Domain을 의존
- **Data**: Domain을 의존

## 데이터 흐름

```
[요청]
UI → ViewModel → UseCase → Repository(Interface) → RepositoryImpl → DataSource → API/DB

[응답]
API/DB → DataSource → RepositoryImpl → UseCase → ViewModel → UI
              ↓              ↓              ↓           ↓
            (DTO)    (DTO → Entity)    (Entity)    (UiState)
```

| 위치 | 변환 |
|------|------|
| DataSource | API 호출, DTO 반환 |
| RepositoryImpl | DTO → Domain Model (Entity) 변환 |
| ViewModel | Domain Model → UiState 변환 |

## 네비게이션 구조

```
AppNavHost (상위 NavController)
├── main         → MainScreen       (바텀네비 ⭕)
│   ├── home     → HomeScreen
│   ├── search   → SearchScreen
│   ├── profile  → ProfileScreen
│   └── settings → SettingsScreen
```

## 사용 방법

### 1. 패키지명 변경
`com.example.yourapp` → 본인 패키지명으로 변경

### 2. BASE_URL 설정
```kotlin
// di/NetworkModule.kt
private const val BASE_URL = "https://your-api.com/"
```

### 3. API 추가
```kotlin
// data/remote/api/ApiService.kt
interface ApiService {
    @GET("your-endpoint")
    suspend fun getData(): YourResponse
}
```

### 4. 새 기능 추가 순서
1. `domain/model` - 모델 정의
2. `domain/repository` - Repository 인터페이스
3. `domain/usecase` - UseCase 작성
4. `data/remote/api` - API 메서드 추가
5. `data/remote/datasource` - DataSource 구현
6. `data/repository` - Repository 구현체
7. `di/RepositoryModule` - 바인딩 추가
8. `presentation/ui` - ViewModel, Screen 작성
