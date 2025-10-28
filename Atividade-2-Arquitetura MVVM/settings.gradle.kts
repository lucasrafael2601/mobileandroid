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
    versionCatalogs {
        // Renomeado para evitar colisão com outro catálogo "libs"
        create("coreLibs") {
            from(files("gradle/libs.versions.toml"))
            // Se precisar importar mais TOMLs, use a MESMA chamada:
            // from(files("gradle/libs.versions.toml", "gradle/extra.versions.toml"))
        }
    }
}

rootProject.name = "My Application"
include(":app")
 