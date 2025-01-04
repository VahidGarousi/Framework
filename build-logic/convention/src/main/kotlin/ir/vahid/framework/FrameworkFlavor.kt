package ir.vahid.framework


import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor

enum class FlavorDimension {
    MODE
}

// The content for the app can either come from local static data which is useful for demo
// purposes, or from a production backend server which supplies up-to-date, real content.
// These two product flavors reflect this behaviour.
enum class FrameworkFlavor(
    val dimension: FlavorDimension,
    val applicationIdSuffix: String? = null,
    val versionName: String,
    val versionCode: Int
) {
    DEVELOP(
        dimension = FlavorDimension.MODE,
        applicationIdSuffix = ".develop",
        versionCode = 1,
        versionName = "1.0.0"
    ),
    STAGING(
        dimension = FlavorDimension.MODE,
        applicationIdSuffix = ".staging",
        versionCode = 1,
        versionName = "1.0.0"
    ),
    PRODUCTION(
        dimension = FlavorDimension.MODE,
        applicationIdSuffix = "",
        versionCode = 1,
        versionName = "1.0.0"
    )
}

fun configureFlavors(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    flavorConfigurationBlock: ProductFlavor.(flavor: FrameworkFlavor) -> Unit = {},
) {
    commonExtension.apply {
        FlavorDimension.values().forEach { flavorDimension ->
            flavorDimensions += flavorDimension.name
        }

        productFlavors {
            FrameworkFlavor.values().forEach { frameworkFlavor ->
                create(frameworkFlavor.name.lowercase()) {
                    dimension = frameworkFlavor.dimension.name
                    flavorConfigurationBlock(this, frameworkFlavor)
                    if (this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
                        if (frameworkFlavor.applicationIdSuffix != null) {
                            applicationIdSuffix = frameworkFlavor.applicationIdSuffix
                        }
                        versionCode = frameworkFlavor.versionCode
                        versionName = frameworkFlavor.versionName
                    }
                }
            }
        }
    }
}
