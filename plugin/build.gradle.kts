import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.20"
    `java-gradle-plugin`
    `maven-publish`
}

apply(from = "publish.gradle.kts")

val buildVersion = "1.0.0"
val groupName = "io.epavlov.telegram.publish"

group = groupName
version = buildVersion

repositories {
    mavenCentral()
    google()
}

tasks.withType<JavaCompile> {
    sourceCompatibility = JavaVersion.VERSION_1_8.toString()
    targetCompatibility = JavaVersion.VERSION_1_8.toString()
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}


gradlePlugin {
    plugins {
        create("TelegramPublishPlugin") {
            id = "io.epavlov.telegram.publish.plugin"
            implementationClass = "io.epavlov.telegram.publish.gradle.TelegramPublishPlugin"
        }
    }
}

dependencies {
    val ktorVersion = "2.1.3"
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.android.tools.build:gradle:4.2.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    // Ktor
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

}