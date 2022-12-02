plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.20"
    `java-gradle-plugin`
}

group = "io.epvalov.telegram.publish"


val buildVersion = "1.0.0"
version = buildVersion

repositories {
    mavenCentral()
    google()
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.android.tools.build:gradle:4.2.2")
}