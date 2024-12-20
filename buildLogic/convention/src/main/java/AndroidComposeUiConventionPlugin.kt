import com.tasky.convention.addUiLayerDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


class AndroidComposeUiConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {

        target.run {

            pluginManager.run {
                apply("tasky.android.library.compose")
            }

            dependencies {
                addUiLayerDependencies(target)
            }

        }

    }
}