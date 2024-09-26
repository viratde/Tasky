import com.android.build.api.dsl.LibraryExtension
import com.tasky.convention.Extension
import com.tasky.convention.configureBuildTypes
import com.tasky.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure


class AndroidLibraryComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {

        target.run {

            pluginManager.run {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }



            extensions.configure<LibraryExtension> {

                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                configureKotlinAndroid(this)
                configureBuildTypes(this, Extension.APPLICATION)

            }

        }

    }
}