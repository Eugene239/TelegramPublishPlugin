import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.20"
    `java-gradle-plugin`
    `maven-publish`
}

group = "io.epvalov.telegram.publish"

val buildVersion = "1.0.0-SNAPSHOT"
version = buildVersion

repositories {
    mavenCentral()
    google()
}

tasks.withType<JavaCompile> {
    sourceCompatibility = JavaVersion.VERSION_1_8.toString()
    targetCompatibility = JavaVersion.VERSION_1_8.toString()
}
tasks.withType<KotlinCompile>{
    kotlinOptions.jvmTarget = "1.8"
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "io.epavlov.telegram.publish"
            artifactId = "plugin"
            version = project.version.toString()

            from(components["kotlin"])
        }
    }
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
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.android.tools.build:gradle:4.2.2")
}