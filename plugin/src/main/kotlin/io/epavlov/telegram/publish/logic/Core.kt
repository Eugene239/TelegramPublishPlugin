package io.epavlov.telegram.publish.logic

import io.epavlov.telegram.publish.logic.network.TelegramApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope


internal object Core {

    suspend fun exec(config: Configuration) = coroutineScope {
        val message = "Version: ${config.version}\n\n${config.releaseNotes}"

        TelegramApi.sendMessage(
            botToken = config.botToken,
            chatId = config.chatId,
            message = message
        )

        config.tasks.forEach { task ->
            val output = task.outputPath
            val regex = task.fileRegex.toRegex()
            output.listFiles()
                ?.filter { regex.matches(it.name) }
                ?.map {
                    async {
                        TelegramApi.sendDocument(
                            botToken = config.botToken,
                            chatId = config.chatId,
                            version = config.version,
                            file = it,
                            prefix = task.prefix
                        )
                    }
                }?.awaitAll()
        }


    }
}