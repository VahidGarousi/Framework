import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.AbstractAppExtension
import com.google.devtools.ksp.gradle.KspExtension
import ir.vahid.framework.configureGeneratedKoinApplication
import ir.vahid.framework.configureKoinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class KoinAndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) = project.run {
        pluginManager.apply("com.google.devtools.ksp")

        extensions.configure<KspExtension> {
            arg("KOIN_CONFIG_CHECK", "true")
        }

        val abstractAppExtension = extensions.getByType<AbstractAppExtension>()
        configureGeneratedKoinApplication(abstractAppExtension)
        val applicationExtension = extensions.getByType<ApplicationExtension>()
        configureKoinAndroid(applicationExtension)
    }
}
