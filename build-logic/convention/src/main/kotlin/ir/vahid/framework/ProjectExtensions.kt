package ir.vahid.framework

import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

val Project.moduleInfo
    get() = extensions.getByType(ModuleInfoExtension::class)


inline fun Project.detektGradle(crossinline configure: DetektExtension.() -> Unit) =
    extensions.configure<DetektExtension> {
        configure()
    }
