package io.github.eugene239.gradle.publish.telegram

import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import java.io.File

interface TelegramPublishExtension {
    val taskList: ListProperty<GradlePublishTask>
    val botToken: Property<String>
    val chatId: Property<String>
    val releaseNotes: Property<String>
    val releaseNotesFile: Property<File>
    val version: Property<String>
}
