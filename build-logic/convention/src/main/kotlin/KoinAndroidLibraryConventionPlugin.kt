import com.android.build.gradle.LibraryExtension
import com.google.devtools.ksp.gradle.KspExtension
import ir.vahid.framework.configureGeneratedKoinLibrary
import ir.vahid.framework.configureKoinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class KoinAndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) = project.run {
        pluginManager.apply("com.google.devtools.ksp")
        extensions.configure<KspExtension> {
            arg("KOIN_CONFIG_CHECK", "true")
        }
        val libraryExtension = extensions.getByType<LibraryExtension>()
        configureGeneratedKoinLibrary(libraryExtension)
        configureKoinAndroid(libraryExtension)
    }
}
