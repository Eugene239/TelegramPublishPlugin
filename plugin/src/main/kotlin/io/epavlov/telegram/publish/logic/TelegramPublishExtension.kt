package io.epavlov.telegram.publish.logic

import org.gradle.api.provider.Property

interface TelegramPublishExtension {
    val text: Property<String>
}