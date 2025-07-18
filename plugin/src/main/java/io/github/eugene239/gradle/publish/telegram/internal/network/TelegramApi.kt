package io.github.eugene239.gradle.publish.telegram.internal.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import java.io.File

object TelegramApi {
    private const val BASE_URL = "https://api.telegram.org"

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
    }

    suspend fun sendMessage(
        botToken: String,
        chatId: String,
        message: String
    ) {
        runCatching {
            println(">>>>> Sending ReleaseNotes")
            val response = client.post("$BASE_URL/bot${botToken}/sendMessage") {
                parameter("chat_id", chatId)
                parameter("text", message)
            }
            println(">>>>> ReleaseNotes status: ${response.status.value}")
            printErrorIfExists(response)
        }.onFailure {
            it.printStackTrace()
        }
    }


    suspend fun sendDocument(
        botToken: String,
        chatId: String,
        prefix: String,
        version: String,
        file: File
    ) {
        runCatching {
            val fileName = "$prefix$version.${file.extension}"
            println(">>>>> Sending $file")
            val response = client.submitFormWithBinaryData("$BASE_URL/bot${botToken}/sendDocument",
                formData {
                    append("document", file.readBytes(), Headers.build {
                        append(HttpHeaders.ContentDisposition, "filename=${fileName}")
                    })
                }) {
                parameter("chat_id", chatId)
            }
            println(">>>>> $fileName status: ${response.status.value}")
            printErrorIfExists(response)
        }.onFailure {
            it.printStackTrace()
        }
    }

    private suspend fun printErrorIfExists(response: HttpResponse) {
        if (!response.status.isSuccess()) {
            val json = response.body<JsonElement>()
            System.err.println(json.toString())
        }
    }
}