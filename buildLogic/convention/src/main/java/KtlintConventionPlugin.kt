import com.tasky.convention.configureKtlint
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jlleitschuh.gradle.ktlint.KtlintExtension

class KtlintConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {

            pluginManager.run {
                apply("org.jlleitschuh.gradle.ktlint")
            }

            configureKtlint()

        }
    }
}