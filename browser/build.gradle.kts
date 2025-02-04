import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "browser"
        browser {
            val isProdBuild = gradle.startParameter.taskNames.contains("buildFatJar")
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                if (isProdBuild) {
                    sourceMaps = false
                }

                outputFileName = "dev.junker.browser.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }
    
    sourceSets {
        wasmJsMain.dependencies {
            implementation(project(":shared"))
            implementation(libs.kotlinx.html)
            implementation(libs.kotlinx.browser)
        }
    }
}

val compiledScripts: NamedDomainObjectProvider<Configuration> by configurations.registering {
    isCanBeConsumed = true
    isCanBeResolved = false
}

artifacts {
    add(compiledScripts.name, tasks.named("wasmJsBrowserDistribution"))
}
