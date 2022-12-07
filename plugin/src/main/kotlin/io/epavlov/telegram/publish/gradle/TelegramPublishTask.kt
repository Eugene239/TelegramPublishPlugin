package io.epavlov.telegram.publish.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class TelegramPublishTask : DefaultTask() {
    @Input
    abstract fun getBuilds(): ListProperty<String>


    @TaskAction
    fun publish() {
        println("<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>")
        println(">>>>>        PUBLISH START         <<<<<")
        exec()
        println(">>>>>        PUBLISH END           <<<<<")
        println("<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>")

    }


    private fun exec() {
//        project.tasks.forEach {
//            println("[TASK]: ${it.name}")
//        }
//        println()


        getBuilds().get().forEach { build ->
            val task = project.tasks.findByName(build) ?: throw Exception("Can't find task with name: $build")
            println("[$build] task did work: ${task.didWork}")
            println("[$build] task has outputs :${task.outputs.hasOutput}")
        }
//        doLast {
//            println("DO LAST MICH")
//        }
    }
}