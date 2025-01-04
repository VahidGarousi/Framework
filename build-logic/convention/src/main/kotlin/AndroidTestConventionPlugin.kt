import com.android.build.gradle.TestExtension
import ir.vahid.framework.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.test")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<TestExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = TARGET_SDK_VERSION
//                configureGradleManagedDevices(this)
            }
        }
    }

}

const val TARGET_SDK_VERSION = 35
