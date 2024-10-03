import com.android.build.api.dsl.LibraryExtension
import com.tasky.convention.Extension
import com.tasky.convention.addAndroidTestDependency
import com.tasky.convention.configureBuildTypes
import com.tasky.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies


class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {

        target.run {

            pluginManager.run {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("tasky.ktlint")
                apply("de.mannodermaus.android-junit5")
            }


            extensions.configure<LibraryExtension> {

                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                configureKotlinAndroid(this)
                configureBuildTypes(this, Extension.LIBRARY)

                dependencies {
                    addAndroidTestDependency(target)
                }

            }

        }

    }
}