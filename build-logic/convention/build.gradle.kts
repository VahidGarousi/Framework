import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "ir.vahid.framework.buildlogic"


// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
    implementation(libs.truth)
}


tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = libs.plugins.framework.android.application.compose.get().pluginId
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = libs.plugins.framework.android.application.asProvider().get().pluginId
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = libs.plugins.framework.android.library.compose.get().pluginId
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = libs.plugins.framework.android.library.asProvider().get().pluginId
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidTest") {
            id = libs.plugins.framework.android.test.get().pluginId
            implementationClass = "AndroidTestConventionPlugin"
        }
        register("androidFlavors") {
            id = libs.plugins.framework.android.application.flavors.get().pluginId
            implementationClass = "AndroidApplicationFlavorsConventionPlugin"
        }
        register("jvmLibrary") {
            id = libs.plugins.framework.jvm.library.get().pluginId
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("frameworkAndroidPublisher") {
            id = libs.plugins.framework.android.publisher.get().pluginId
            implementationClass = "AndroidLibraryPublisherConventionPlugin"
        }
        register("frameworkJvmPublisher") {
            id = libs.plugins.framework.jvm.publisher.get().pluginId
            implementationClass = "JvmLibraryPublisherConventionPlugin"
        }
        register("frameworkAndroidKtlint") {
            id = libs.plugins.framework.android.ktlint.get().pluginId
            implementationClass = "KotlinterConventionPlugin"
        }
        register("androidApplicationJacoco") {
            id = libs.plugins.framework.android.application.jacoco.get().pluginId
            implementationClass = "AndroidApplicationJacocoConventionPlugin"
        }
        register("androidLibraryJacoco") {
            id = libs.plugins.framework.android.library.jacoco.get().pluginId
            implementationClass = "AndroidLibraryJacocoConventionPlugin"
        }
        register("androidDetekt") {
            id = libs.plugins.framework.android.detekt.get().pluginId
            implementationClass = "AndroidDetektConventionPlugin"
        }
        register("kotest") {
            id = libs.plugins.framework.kotest.get().pluginId
            implementationClass = "KotestConventionPlugin"
        }
    }
}
