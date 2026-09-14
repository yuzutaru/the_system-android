# the_system-android

Native Android app for **The System** — Kotlin 2 · Jetpack Compose · Hilt · Room.

Part of the [The System workspace](https://github.com/yuzutaru/the_system). Game rules come from
[`docs/domain.md`](https://github.com/yuzutaru/the_system/blob/main/docs/domain.md); architecture from
[`docs/architecture.md`](https://github.com/yuzutaru/the_system/blob/main/docs/architecture.md).

## Architecture — modular feature architecture

Thin `:app` shell + one top-level Gradle module per feature + shared `core` modules.
Features never depend on each other.

```
the_system-android/
├── app/                    thin shell: Hilt graph + Compose Nav host
├── home/                   landing / onboarding screen
├── workout/                reference vertical slice (Compose + ViewModel + use case)
├── stats/
├── quest/
├── character/
├── core/
│   ├── domain/             pure Kotlin: entities, formulas, repository interfaces
│   ├── data/               repository implementations + Hilt bindings
│   ├── database/           Room entities + DAOs
│   ├── designsystem/       Compose theme + components
│   └── navigation/         type-safe routes
└── gradle/libs.versions.toml
```

Dependency direction: `home|workout|stats|quest|character → core/domain` (interfaces).
`core/data` provides the implementations and binds them with Hilt. `app` wires
everything together.

## Requirements

- JDK 17
- Android SDK 35

## Build & test

```bash
./gradlew test                 # unit tests (domain + features)
./gradlew :app:assembleDebug   # build the debug APK
./gradlew lint                 # Android lint
```

## Conventions

- Domain is pure Kotlin/JVM — no Android imports.
- Features depend on `core:domain` and `core:designsystem` only.
- Dependencies are declared in `gradle/libs.versions.toml`.
- Conventional Commits.

## License

[MIT](./LICENSE)
