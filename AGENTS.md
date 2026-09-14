# AGENTS.md — the_system-android

Read the [workspace root AGENTS.md](https://github.com/yuzutaru/the_system/blob/main/AGENTS.md) first, then
[`docs/architecture.md`](https://github.com/yuzutaru/the_system/blob/main/docs/architecture.md) and
[`docs/domain.md`](https://github.com/yuzutaru/the_system/blob/main/docs/domain.md).

## Commands

```bash
./gradlew test                     # all unit tests
./gradlew :core:domain:test        # domain tests only
./gradlew :app:assembleDebug       # build debug APK
./gradlew lint                     # Android lint
```

Use `--no-configuration-cache` if you hit configuration-cache issues.

## Layout

| Module | Purpose |
| :--- | :--- |
| `:app` | Thin shell: `TheSystemApplication`, `MainActivity`, `AppRoot`, `AppViewModel` |
| `:core:domain` | Pure Kotlin: entities, `XPCalculator`, `LevelCurve`, `CharacterClass`, repository interfaces |
| `:core:data` | `WorkoutRepositoryImpl`, `CharacterRepositoryImpl`, Hilt `DataModule` |
| `:core:database` | Room entities, DAOs, `TheSystemDatabase` |
| `:core:designsystem` | `TheSystemColors`, `Attribute.tint`, `StatBar` |
| `:core:navigation` | `@Serializable` route objects |
| `:home`, `:workout`, `:quest`, `:stats`, `:character` | `presentation/` (Compose + ViewModel) + use cases |

## Rules

- `:workout`, `:quest`, `:stats`, `:character` may depend on `core:domain` and
  `core:designsystem` only — **never on another feature**.
- `core:domain` is a pure JVM module with no Android dependencies.
- All versions go in `gradle/libs.versions.toml`; never hardcode versions.
- Version-catalog accessors use dots: `libs.compose.ui.tooling`, not `libs.compose.ui-tooling`.
- When a formula changes, update [`docs/domain.md`](https://github.com/yuzutaru/the_system/blob/main/docs/domain.md) and `:core:domain` tests together.
