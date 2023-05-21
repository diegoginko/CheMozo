pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven("https://jitpack.io")
    }
}

rootProject.name = "CheMozo"
include(":androidApp")
include(":shared")