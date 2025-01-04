import java.io.FileInputStream
import java.util.Properties
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import ir.vahid.framework.configureJvmModulePublishing
import ir.vahid.framework.moduleInfo
class JvmLibraryPublisherConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("maven-publish")
            }
            configureJvmModulePublishing()
            configure<PublishingExtension> {
                afterEvaluate {
                    publications {
                        create<MavenPublication>(
                            name = "framework",
                            configuration = {
                                artifactId = moduleInfo.artifactId
                                groupId = moduleInfo.groupId
                                version = version
                                from(components["java"])
                            }
                        )
                    }
                    val publishingConfigPropertiesFile = rootProject.file("publishing-config.properties")
                    val publishingConfigProperties = Properties()
                    publishingConfigProperties.load(FileInputStream(publishingConfigPropertiesFile))
                    repositories {
                        maven {
                            name = publishingConfigProperties["name"] as String
                            url = uri(publishingConfigProperties["url"] as String)
                            credentials {
                                username = publishingConfigProperties["username"] as String
                                password = publishingConfigProperties["password"] as String
                            }
                        }
                    }
                }
            }
        }
    }
}
