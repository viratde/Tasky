import com.android.build.api.dsl.ApplicationExtension
import com.tasky.convention.Extension
import com.tasky.convention.configureBuildTypes
import com.tasky.convention.configureKotlinAndroid
import com.tasky.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure


class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {

        target.run {

            pluginManager.run {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }



            extensions.configure<ApplicationExtension> {

                defaultConfig {

                    applicationId = libs.findVersion("applicationId").get().toString()
                    targetSdk = libs.findVersion("targetSdkVersion").get().toString().toInt()
                    versionCode = libs.findVersion("versionCode").get().toString().toInt()
                    versionName = libs.findVersion("versionName").get().toString()

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                configureKotlinAndroid(this)
                configureBuildTypes(this, Extension.APPLICATION)

            }

        }

    }
}