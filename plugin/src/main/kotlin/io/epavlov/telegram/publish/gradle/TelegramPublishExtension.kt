package io.epavlov.telegram.publish.gradle

import org.gradle.api.provider.ListProperty

interface TelegramPublishExtension {
    val buildConfigs: ListProperty<TelegramPublishBuildConfig>
}
