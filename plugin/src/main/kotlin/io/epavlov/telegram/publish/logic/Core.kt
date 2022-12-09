package io.epavlov.telegram.publish.logic

import io.epavlov.telegram.publish.logic.network.TelegramApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope


internal object Core {

    suspend fun exec(config: Configuration) = coroutineScope {
        val message = "Version: ${config.version}\n\n${config.releaseNotes}"

        val outputs = config.tasks.map { task ->
            val output = task.outputPath
            val regex = task.fileRegex.toRegex()
            val files = output.listFiles()
                ?.filter { regex.matches(it.name) }.orEmpty()

            task to files
        }

        // it's ok to have one output or more
        var hasOutputs = false
        outputs.forEach { pair ->
            if (pair.second.isEmpty()) {
                System.err.println("No outputs from task ${pair.first.taskName}")
            } else {
                hasOutputs = true
            }
        }

        if (!hasOutputs) {
            throw Exception("Check task output creation")
        }

        TelegramApi.sendMessage(
            botToken = config.botToken,
            chatId = config.chatId,
            message = message
        )

        outputs.map {
            val task = it.first
            val files = it.second

            files.map { file ->
                async {
                    TelegramApi.sendDocument(
                        botToken = config.botToken,
                        chatId = config.chatId,
                        version = config.version,
                        file = file,
                        prefix = task.prefix
                    )
                }
            }
        }.flatten()
            .awaitAll()
    }

}