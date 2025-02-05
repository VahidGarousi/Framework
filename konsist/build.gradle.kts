plugins {
    alias(libs.plugins.framework.android.library)
}

android {
    namespace = "ir.vahid.konsist"
}

dependencies {
    testImplementation(libs.bundles.kotest)
    testImplementation(libs.konsist)
}
