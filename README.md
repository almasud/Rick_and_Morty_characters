# Rick & Morty Explorer

An Android showcase app that explores the [Rick & Morty API](https://rickandmortyapi.com/) with a modern **clean architecture** stack. The project demonstrates how to keep domain logic isolated, data pipelines testable, and the UI reactive by design.

---

## Architecture At A Glance

- **Presentation layer**: Jetpack Compose screens + ViewModels expose immutable UI state (`Flow`, `PagingData`). Navigation is handled through a single graph.
- **Domain layer**: Pure Kotlin models, repositories, and use cases. No Android dependencies—business logic stays platform agnostic.
- **Data layer**: Remote sources (Retrofit), local cache (Room), repositories that orchestrate the flow, plus a Paging `RemoteMediator` to bridge network and database.
- **Core module**: Shared utilities (database, networking helpers, error → UI text mappers, scaffolds).

This separation keeps dependencies flowing inward only (presentation → domain → data), making each layer independently testable and swappable.

---

## Directory Map

```text
app/
├── src/main/java/com/github/almasud/rickandmorty/
│   ├── character/
│   │   ├── data/
│   │   │   ├── di/                 # Hilt bindings for data sources & repos
│   │   │   ├── mappers/            # DTO ↔ entity ↔ domain mappers
│   │   │   ├── remote/             # Retrofit API + DTOs
│   │   │   └── sources/paging/     # Paging RemoteMediator
│   │   ├── domain/
│   │   │   ├── models/             # Pure Kotlin domain models
│   │   │   └── usecases/           # Interactors per feature
│   │   └── presentation/           # Compose screens + ViewModels
│   ├── character_details/          # Mirrors character feature for detail view
│   └── core/
│       ├── data/                   # Shared DB + networking glue
│       ├── domain/                 # Shared domain contracts/errors
│       └── presentation/           # UI scaffolds, navigation, UiText helpers
└── build.gradle.kts                # Module-level build config
```

---

## Tech Stack & Tooling

| Layer / Concern      | Libraries & Tools                                                                                           |
|----------------------|-------------------------------------------------------------------------------------------------------------|
| Language & Build     | Kotlin (JVM 11), Gradle KTS, AGP 8.x, KSP                                                                   |
| DI                   | Hilt                                                                                                        |
| UI                   | Jetpack Compose, Material 3, Navigation Compose, Coil 3 for image loading                                   |
| State & Async        | Kotlin Coroutines, StateFlow, Paging 3 (runtime + compose)                                                  |
| Data                 | Retrofit + Gson, Room (with Paging integration), custom `RemoteResult` wrapper and `handleRestApiCall`      |
| Architecture Helpers | Clean architecture modules, DTO/entity/domain mappers, RemoteMediator for offline-first pagination         |
| Tooling Extras       | Compose BOM, Kotlinx serialization helpers, logging via `Log.d` for mediator lifecycle                      |

---

## Feature Highlights

- **Paginated character catalog** backed by Room cache and refreshed via `CharacterRemoteMediator`.
- **Character detail sheet** that hydrates from local cache via a dedicated use case, keeping the screen resilient to config changes.
- **Error surfacing** through a shared `UiText` abstraction, enabling easy localization and testing.
- **Composable-first UI** with custom hero cards, gradient backgrounds, and snackbars for transient errors.

---

## Getting Started

1. Ensure you have JDK 11+ and the latest Android Studio.
2. Clone the repository and open the root `Rick and Morty` directory.
3. Sync Gradle; the project uses the version catalog in `gradle/libs.versions.toml`.
4. Run `./gradlew :app:assembleDebug` (or from Android Studio) to build.

The app targets API 24+ and uses the latest stable Compose + Paging stack for an opinionated, production-ready architecture sample.

Happy hacking! 🛸
