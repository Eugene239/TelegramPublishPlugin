import java.io.File
import java.io.FileInputStream
import java.util.*

apply(plugin = "maven-publish")


val localProperties = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, "plugin.local.properties")))
}


configure<PublishingExtension> {
    repositories {
        maven {
            name = "Github"
            url = uri("https://maven.pkg.github.com/Eugene239/TelegramPublishPlugin")
            credentials {
                username = localProperties.getProperty("GITHUB_ACTOR")
                password = localProperties.getProperty("GITHUB_TOKEN")
            }
        }
    }
}
