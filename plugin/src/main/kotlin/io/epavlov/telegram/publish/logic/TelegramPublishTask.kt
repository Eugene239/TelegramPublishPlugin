package io.epavlov.telegram.publish.logic

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class TelegramPublishTask : DefaultTask() {
    @Input
    abstract fun getText(): Property<String>


    @TaskAction
    fun publish() {
        println("<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>")
        println(">>>>>        PUBLISH START         <<<<<")
        println(getText().get())
        println(">>>>>        PUBLISH END           <<<<<")
        println("<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>")

    }
}