pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TheSystem"

include(":app")

include(":core:domain")
include(":core:data")
include(":core:database")
include(":core:designsystem")
include(":core:navigation")

include(":workout")
include(":stats")
include(":quest")
include(":character")
include(":home")
