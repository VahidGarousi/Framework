import ir.vahid.framework.FrameworkBuildType

plugins {
    alias(libs.plugins.framework.android.application)
    alias(libs.plugins.framework.android.application.compose)
    alias(libs.plugins.framework.android.application.flavors)
    alias(libs.plugins.framework.android.application.jacoco)
    alias(libs.plugins.framework.kotest)
}

android {
    namespace = "ir.vahid.framework"
    compileSdk = 35

    defaultConfig {
        applicationId = namespace
        minSdk = 21
        targetSdk = 35


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            versionNameSuffix = FrameworkBuildType.DEBUG.versionNameSuffix
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}/")
            excludes.add("META-INF/LICENSE.md")
            excludes.add("META-INF/LICENSE-notice.md")
        }
    }
}

configurations.all {
    resolutionStrategy {
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-debug")
    }
}
dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.material3.adaptive.layout)
    implementation(libs.androidx.compose.material3.adaptive.navigation)
    implementation(libs.androidx.compose.material3.navigationSuite)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.tracing.ktx)
    implementation(libs.androidx.window.core)
    implementation(libs.coil.kt)
    implementation(libs.kotlinx.serialization.json)
    implementation(projects.core.designSystem)
    implementation(projects.core.domain)

    testImplementation(libs.androidx.test.rules)

    //    testImplementation(libs.kotest.assertions.core)
    //    testImplementation(libs.kotest.property)
    //    testImplementation(libs.kotest.runner.junit5)
    androidTestImplementation(libs.androidx.compose.ui.test)
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.kotlin.test)
    androidTestImplementation(libs.kotlinx.coroutines.test)
}
dependencyGuard {
    configuration("productionReleaseRuntimeClasspath")
}
