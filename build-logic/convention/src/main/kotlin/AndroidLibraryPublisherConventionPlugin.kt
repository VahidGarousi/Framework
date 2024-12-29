import ir.vahid.framework.configureAndroidModulePublishing
import ir.vahid.framework.moduleInfo
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import java.io.FileInputStream
import java.util.Properties

class AndroidLibraryPublisherConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("maven-publish")
            }
            configureAndroidModulePublishing()
            configure<PublishingExtension> {
                afterEvaluate {
                    publications {
                        create<MavenPublication>(
                            name = "framework",
                            configuration = {
                                artifactId = moduleInfo.artifactId
                                groupId = moduleInfo.groupId
                                version = version
                                from(components["release"])
                            }
                        )
                    }
                    val publishingConfigPropertiesFile =
                        rootProject.file("publishing-config.properties")
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
