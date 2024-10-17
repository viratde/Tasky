import com.tasky.convention.addJvmTestDependency
import com.tasky.convention.configureKotlinJvm
import com.tasky.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType


class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {

            pluginManager.run {
                apply("org.jetbrains.kotlin.jvm")
                apply("tasky.ktlint")
            }

            project.tasks.withType<Test>().configureEach {
                useJUnitPlatform()
                testLogging {
                    events("passed", "skipped", "failed")
                }
            }
            configureKotlinJvm()

            dependencies {
                addJvmTestDependency(target)
                "implementation"(project.libs.findLibrary("kotlinx.coroutines.core").get())
            }
        }
    }
}