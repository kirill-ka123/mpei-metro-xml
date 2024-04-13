import org.gradle.kotlin.dsl.`java-gradle-plugin`
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.version
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.androidApplication: PluginDependencySpec
    get() = id("com.android.application")

val PluginDependenciesSpec.kotlinAndroid: PluginDependencySpec
    get() = id("org.jetbrains.kotlin.android")

val PluginDependenciesSpec.kapt: PluginDependencySpec
    get() = kotlin("kapt") version "1.9.10"

val PluginDependenciesSpec.kotlinParcelize: PluginDependencySpec
    get() = id("kotlin-parcelize")
