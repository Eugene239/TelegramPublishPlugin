package io.github.eugene239.gradle.publish.telegram

import java.io.File

data class GradlePublishTask(
    val taskName: String,
    val outputPath: File,
    val prefix: String = "",
    val fileRegex: String = ".*\\.apk"
)