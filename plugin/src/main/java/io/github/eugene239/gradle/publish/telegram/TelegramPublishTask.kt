package io.github.eugene239.gradle.publish.telegram

import io.github.eugene239.gradle.publish.telegram.internal.Core
import io.github.eugene239.gradle.publish.telegram.internal.toConfiguration
import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class TelegramPublishTask : DefaultTask() {
    @Input
    abstract fun getConfig(): Property<TelegramPublishExtension>

    @TaskAction
    fun publish() {
        println("<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>")
        println(">>>>>        PUBLISH START         <<<<<")
        println()

        runBlocking {
            Core.exec(getConfig().get().toConfiguration())
        }

        println()
        println(">>>>>        PUBLISH END           <<<<<")
        println("<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>")
    }

}
