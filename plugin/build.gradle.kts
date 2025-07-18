import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.gradle.publish)
    `maven-publish`
    groovy
}


val buildVersion = "1.1.0"
val groupName = "io.github.eugene239"
group = groupName
version = buildVersion

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("gradle") {
            groupId = groupName
            artifactId = "gradle-publish-telegram"
            version = buildVersion
            from(components["java"])

            pom {
                url.set("https://github.com/Eugene239/TelegramPublishPlugin")

                scm {
                    url.set("https://github.com/Eugene239/TelegramPublishPlugin")
                }
            }
        }
    }
}

gradlePlugin {
    website.set("https://github.com/Eugene239/TelegramPublishPlugin")
    vcsUrl.set("https://github.com/Eugene239/TelegramPublishPlugin")
    plugins {
        create("TelegramPublishPlugin") {
            id = "io.github.eugene239.gradle.publish.telegram"
            implementationClass =
                "io.github.eugene239.gradle.publish.telegram.TelegramPublishPlugin"
            displayName = "Telegram Publish Plugin"
            description = "Publish Android build to telegram chat"
            @Suppress("UnstableApiUsage")
            tags.set(listOf("publish", "telegram"))
        }
    }
}

dependencies {
    implementation(libs.kotlinx.coroutines)

    // Ktor
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.ktor.client)
    implementation(libs.ktor.serialization.kotlinx.json)
}