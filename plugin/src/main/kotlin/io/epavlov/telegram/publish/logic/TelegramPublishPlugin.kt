package io.epavlov.telegram.publish.logic

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class TelegramPublishPlugin : Plugin<Project> {

    @Suppress("ObjectLiteralToLambda")
    override fun apply(target: Project) {
        val extension: TelegramPublishExtension =
            target.extensions.create("telegramPublishConfig", TelegramPublishExtension::class.java)

        target.afterEvaluate {
            val action = object : Action<TelegramPublishTask> {
                override fun execute(task: TelegramPublishTask) {
                    task.getText().set(extension.text)
                }
            }
            target.tasks.register("telegramPublish", TelegramPublishTask::class.java, action)
        }
    }
}