val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
}

group = "dev.junker"
version = "0.0.1"

application {
    mainClass.set("dev.junker.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

ktor {
    fatJar {
        archiveFileName.set("dev.junker.server.jar")
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
    maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers") }
}

val compiledBrowserScripts: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

dependencies {
    compiledBrowserScripts(project(":browser")) {
        targetConfiguration = "compiledScripts"
    }

    implementation(project(":shared"))
    implementation("io.ktor:ktor-server-html-builder-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-default-headers:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages:$ktorVersion")
    implementation("org.jetbrains:kotlin-css-jvm:1.0.0-pre.129-kotlin-1.4.20")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")

    constraints {
        implementation("io.netty:netty-common:4.1.115.Final") {
            because("GHSA-xq3w-v528-46rv")
        }
    }
}

val copyCompiledBrowserScripts: TaskProvider<Copy> by tasks.registering(Copy::class) {
    from(compiledBrowserScripts)
    into(project.layout.buildDirectory.dir("resources/main/static/scripts"))
}

tasks.named("processResources") {
    dependsOn(copyCompiledBrowserScripts)
}
