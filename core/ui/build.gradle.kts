plugins {
    alias(libs.plugins.framework.android.library)
    alias(libs.plugins.framework.android.library.compose)
    alias(libs.plugins.framework.android.library.jacoco)
    alias(libs.plugins.roborazzi)
}
android {
    namespace = "ir.vahid.core.ui"
}


dependencies {
    implementation(projects.core.platform)
}
