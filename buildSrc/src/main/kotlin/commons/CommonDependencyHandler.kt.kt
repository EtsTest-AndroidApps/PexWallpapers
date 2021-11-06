package commons

import Modules
import dependencies.TestDependencies
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

/*
Credit: https://github.com/igorwojda/android-showcase/blob/master/buildSrc/src/main/kotlin/CommonModuleDependency.kt

Define common dependencies, so they can be easily updated across another modules
 */
fun DependencyHandler.addTestDependencies() {
    testImplementation(project(Modules.testShared))

    testImplementation(TestDependencies.test_core)
    testImplementation(TestDependencies.arch_core)
    testImplementation(TestDependencies.espresso_core)
    testImplementation(TestDependencies.coroutinesTest)
    testImplementation(TestDependencies.turbine)
    testImplementation(TestDependencies.kotest)
    testImplementation(TestDependencies.truth_ext)
    testImplementation(TestDependencies.jupiter)
    testImplementation(TestDependencies.jupiterEngine)
    testImplementation(TestDependencies.mockk)
}

/*
 * These extensions mimic the extensions that are generated on the fly by Gradle.
 * They are used here to provide above dependency syntax that mimics Gradle Kotlin DSL
 * syntax in module\build.gradle.kts files.
 */
@Suppress("detekt.UnusedPrivateMember")
fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

@Suppress("detekt.UnusedPrivateMember")
fun DependencyHandler.api(dependencyNotation: Any): Dependency? =
    add("api", dependencyNotation)

@Suppress("detekt.UnusedPrivateMember")
fun DependencyHandler.kapt(dependencyNotation: Any): Dependency? =
    add("kapt", dependencyNotation)

private fun DependencyHandler.testImplementation(dependencyNotation: Any): Dependency? =
    add("testImplementation", dependencyNotation)

private fun DependencyHandler.androidTestImplementation(dependencyNotation: Any): Dependency? =
    add("androidTestImplementation", dependencyNotation)

@Suppress("unchecked_cast", "nothing_to_inline", "detekt.UnsafeCast")
private inline fun <T> uncheckedCast(obj: Any?): T = obj as T