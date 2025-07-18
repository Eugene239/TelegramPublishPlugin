package io.github.eugene239.gradle.publish.telegram.internal

import io.github.eugene239.gradle.publish.telegram.GradlePublishTask
import io.github.eugene239.gradle.publish.telegram.TelegramPublishExtension

internal data class Configuration(
    val tasks: List<GradlePublishTask>,
    val botToken: String,
    val chatId: String,
    val releaseNotes: String?,
    val version: String,
)


internal fun TelegramPublishExtension.toConfiguration(): Configuration {
    val releaseNotes = if (releaseNotes.isPresent) {
        releaseNotes.get()
    } else {
        if (releaseNotesFile.isPresent) {
            val file = releaseNotesFile.get()
            file.inputStream().readBytes().toString(Charsets.UTF_8)
        } else {
            null
        }
    }
    return Configuration(
        tasks = taskList.get(),
        botToken = botToken.get(),
        chatId = chatId.get(),
        version = version.get(),
        releaseNotes = releaseNotes
    )
}
