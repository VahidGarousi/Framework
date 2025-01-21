import ir.vahid.framework.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

class KotestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                if (plugins.hasPlugin("com.android.library") || plugins.hasPlugin("com.android.application")) {
                    apply("kotlin-android")
                }
            }

            configureKotestDependencies()
            configureTestTasks()

            if (plugins.hasPlugin("com.android.library") || plugins.hasPlugin("com.android.application")) {
                configureAndroidSpecifics()
            } else {
                configureJvmSpecifics()
            }
        }
    }

    private fun Project.configureKotestDependencies() {
        dependencies {
            add("testImplementation", libs.findLibrary("kotest-property").get())
            add("testImplementation",libs.findLibrary("kotest-assertions-core").get())
            add("testImplementation",libs.findLibrary("kotest-runner-junit5").get())
        }
    }

    private fun Project.configureTestTasks() {
        tasks.withType<Test>().configureEach {
            useJUnitPlatform()
            testLogging {
                events("passed", "skipped", "failed")
                showStandardStreams = true
            }
        }
    }

    private fun Project.configureAndroidSpecifics() {
        extensions.getByType<KotlinAndroidProjectExtension>().apply {
            sourceSets.getByName("test") {
                kotlin.srcDir("src/test/kotlin")
            }
            sourceSets.getByName("androidTest") {
                kotlin.srcDir("src/androidTest/kotlin")
            }
        }
        dependencies {
            add("androidTestImplementation",libs.findLibrary("kotest-assertions-core").get())
            add("androidTestImplementation",libs.findLibrary("kotest-runner-junit5").get())
        }
    }

    private fun Project.configureJvmSpecifics() {
        extensions.getByType<KotlinJvmProjectExtension>().apply {
            sourceSets.getByName("test") {
                kotlin.srcDir("src/test/kotlin")
            }
        }
    }

}
