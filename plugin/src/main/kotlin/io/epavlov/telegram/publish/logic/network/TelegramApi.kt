package io.epavlov.telegram.publish.logic.network

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import java.io.File

object TelegramApi {
    private const val BASE_URL = "https://api.telegram.org"

    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                expectSuccess = true
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
        client.post("$BASE_URL/bot${botToken}/sendMessage") {
            parameter("chat_id", chatId)
            parameter("text", message)
        }
    }


    suspend fun sendDocument(
        botToken: String,
        chatId: String,
        prefix: String,
        version: String,
        file: File
    ) {
        val fileName = "$prefix$version.${file.extension}"
        client.submitFormWithBinaryData("$BASE_URL/bot${botToken}/sendDocument",
            formData {
                append("document", file.readBytes(), Headers.build {
                    append(HttpHeaders.ContentDisposition, "filename=${fileName}")
                })
            }) {
            parameter("chat_id", chatId)
        }
    }


}