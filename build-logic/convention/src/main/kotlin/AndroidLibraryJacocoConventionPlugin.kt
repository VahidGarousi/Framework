import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import ir.vahid.framework.configureJacoco
import org.gradle.kotlin.dsl.apply

class AndroidLibraryJacocoConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "jacoco")
            val androidExtension = extensions.getByType<LibraryExtension>()
            androidExtension.buildTypes.configureEach {
                enableAndroidTestCoverage = true
                enableUnitTestCoverage = true
            }
            configureJacoco(extensions.getByType<LibraryAndroidComponentsExtension>())
        }
    }
}
