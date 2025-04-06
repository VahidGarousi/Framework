pluginManagement {
    repositories {
        maven("https://maven.myket.ir")
        gradlePluginPortal()
        google()
    }
}


dependencyResolutionManagement {
    repositories {
        maven("https://maven.myket.ir")
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"
include(":convention")
