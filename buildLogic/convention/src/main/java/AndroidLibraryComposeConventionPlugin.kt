import com.android.build.api.dsl.LibraryExtension
import com.tasky.convention.Extension
import com.tasky.convention.configureAndroidCompose
import com.tasky.convention.configureBuildTypes
import com.tasky.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType


class AndroidLibraryComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {

        target.run {

            pluginManager.run {
                apply("tasky.android.library")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)

        }

    }
}