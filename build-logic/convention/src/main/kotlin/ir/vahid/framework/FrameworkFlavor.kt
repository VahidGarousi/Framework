package ir.vahid.framework


import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor

@Suppress("EnumEntryName")
enum class FlavorDimension {
    mode
}

// The content for the app can either come from local static data which is useful for demo
// purposes, or from a production backend server which supplies up-to-date, real content.
// These two product flavors reflect this behaviour.
@Suppress("EnumEntryName")
enum class FrameworkFlavor(
    val dimension: FlavorDimension,
    val applicationIdSuffix: String? = null,
    val versionName: String,
    val versionCode: Int
) {
    develop(
        dimension = FlavorDimension.mode,
        applicationIdSuffix = ".develop",
        versionCode = 1,
        versionName = "1.0.0"
    ),
    staging(
        dimension = FlavorDimension.mode,
        applicationIdSuffix = ".staging",
        versionCode = 1,
        versionName = "1.0.0"
    ),
    production(
        dimension = FlavorDimension.mode,
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
                create(frameworkFlavor.name) {
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
