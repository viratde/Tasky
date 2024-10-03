
import com.tasky.convention.configureKotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project


class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {

            pluginManager.run {
                apply("org.jetbrains.kotlin.jvm")
                apply("tasky.ktlint")
            }

            configureKotlinJvm()

        }
    }
}