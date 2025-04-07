package ir.vahid.framework


import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.AbstractAppExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinBaseExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension



internal fun Project.configureGeneratedKoinLibrary(
    commonExtension: LibraryExtension,
){
    commonExtension.apply {
        libraryVariants.forEach { variant ->
            variant.sourceSets.forEach {
                it.javaDirectories += files("build/generated/ksp/${variant.name}/kotlin")
            }
        }
    }
}

internal fun Project.configureGeneratedKoinApplication(
    commonExtension: AbstractAppExtension,
){
    commonExtension.apply {
        applicationVariants.forEach { variant ->
            variant.sourceSets.forEach {
                it.javaDirectories += files("build/generated/ksp/${variant.name}/kotlin")
            }
        }
    }
}

internal fun Project.configureKoinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        dependencies {
            val bom = libs.findLibrary("koin.bom").get()
            add("implementation", platform(bom))
            add("implementation", libs.findLibrary("koin.android").get())
            add("implementation", libs.findLibrary("koin.compose").get())
            add("compileOnly", libs.findLibrary("koin.annotations.core").get())
            add("ksp", libs.findLibrary("koin.annotations.compiler").get())
        }
    }
}
