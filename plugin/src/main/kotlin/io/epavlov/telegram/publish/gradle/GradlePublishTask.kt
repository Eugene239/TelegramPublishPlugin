package io.epavlov.telegram.publish.gradle

import java.io.File

data class GradlePublishTask(
    val taskName: String,
    val outputPath: File,
    val prefix: String = "",
    val fileRegex: String = ".*\\.apk"
)