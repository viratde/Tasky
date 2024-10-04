pluginManagement {
    includeBuild("buildLogic")
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

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "Tasky"
include(":app")
include(":core:data")
include(":core:domain")
include(":core:presentation:designSystem")
include(":core:presentation:ui")
include(":auth:data")
include(":auth:presentation")
include(":auth:domain")
include(":agenda:data")
include(":agenda:presentation")
include(":agenda:domain")
include(":agenda:network")
include(":test-utils")
