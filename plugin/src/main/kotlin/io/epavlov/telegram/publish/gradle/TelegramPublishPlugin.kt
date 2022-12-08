package io.epavlov.telegram.publish.gradle

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
                    val configs = extension.taskList.get()
                    configs.forEach { build ->
                        val gradleTask = target.tasks.findByName(build.taskName)
                            ?: throw Exception("Can't find task with name: $build")
                        task.dependsOn(gradleTask.path)
                    }
                    task.getConfig().set(extension)
                }
            }
            target.tasks.register("telegramPublish", TelegramPublishTask::class.java, action)

        }
    }
}