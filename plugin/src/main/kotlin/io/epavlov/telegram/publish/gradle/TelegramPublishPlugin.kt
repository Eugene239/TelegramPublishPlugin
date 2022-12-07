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
                    val builds = extension.buildConfigs.get().map { it.taskName }
                    builds.forEach { build ->
                        val gradleTask =
                            target.tasks.findByName(build) ?: throw Exception("Can't find task with name: $build")
                        println("dependsOn path by $build")
                        task.dependsOn(gradleTask.path)
                    }
                    task.getBuilds().set(builds)
                }
            }
            target.tasks.register("telegramPublish", TelegramPublishTask::class.java, action)
        }
    }
}