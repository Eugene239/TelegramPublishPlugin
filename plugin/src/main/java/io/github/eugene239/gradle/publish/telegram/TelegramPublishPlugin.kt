package io.github.eugene239.gradle.publish.telegram

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class TelegramPublishPlugin : Plugin<Project> {

    @Suppress("ObjectLiteralToLambda")
    override fun apply(target: Project) {
        val extension: TelegramPublishExtension =
            target.extensions.create("telegramPublishConfig", TelegramPublishExtension::class.java)

        val parent = target.parent?.project ?: target

        target.afterEvaluate {

            val action = object : Action<TelegramPublishTask> {
                override fun execute(task: TelegramPublishTask) {

                    val configs = extension.taskList.get()
                    configs.forEach { build ->
                        val gradleTask = target.tasks.findByName(build.taskName)
                            ?: throw Exception("Can't find task with name: ${build.taskName}")

                        task.dependsOn(gradleTask.path)
                    }
                    task.getConfig().set(extension)
                }
            }
            parent.tasks.register("telegramPublish", TelegramPublishTask::class.java, action)
        }
    }
}