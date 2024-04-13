rootProject.name = "Metro"
include(":app")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            val dependency = when (requested.id.id) {
                "com.android.application" -> "com.android.tools.build:gradle:8.1.0"
                "org.jetbrains.kotlin.android" -> "org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10"
                else -> null
            }
            if (dependency != null) {
                useModule(dependency)
            }
        }
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}