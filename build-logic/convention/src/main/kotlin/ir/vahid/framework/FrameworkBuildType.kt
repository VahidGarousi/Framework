package ir.vahid.framework

/**
 * This is shared between :app and :benchmarks module to provide configurations type safety.
 */
enum class FrameworkBuildType(val versionNameSuffix: String? = null) {
    DEBUG(".debug"),
    RELEASE,
}
