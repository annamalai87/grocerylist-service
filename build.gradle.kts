

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.5.31"

    // Apply the application plugin to add support for building a CLI application in Java.
    application
    id("com.github.johnrengelman.shadow") version "5.0.0"

}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("com.github.jengelman.gradle.plugins:shadow:5.0.0")

    implementation("io.ktor:ktor-server-core:1.6.7")
    implementation("io.ktor:ktor-server-netty:1.6.7")
    implementation("ch.qos.logback:logback-classic:1.2.10")
    implementation("io.ktor:ktor-gson:1.6.7")
    implementation("io.insert-koin:koin-core:3.1.5")
    implementation("org.mongodb:mongodb-driver-reactivestreams:4.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.6.0")


    implementation("io.insert-koin:koin-ktor:3.1.5")


    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")


    // This dependency is used by the application.
    implementation("com.google.guava:guava:31.0.1-jre")

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}


application {
    mainClassName = "grocerylist.AppKt"
}

sourceSets.main {
    java.srcDirs("src/main/kotlin")
}


tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    shadowJar {
        archiveClassifier.set("")
    }
}