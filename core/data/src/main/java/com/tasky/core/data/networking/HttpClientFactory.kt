package com.tasky.core.data.networking

import com.tasky.core.domain.AuthInfoStorage
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientFactory(
    private val authInfoStorage: AuthInfoStorage
) {

    fun build(): HttpClient {
        return HttpClient(CIO) {


            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }

            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
            }

            install(Auth) {

                bearer {

                    loadTokens {
                        val authInfo = authInfoStorage.get()
                        BearerTokens(
                            accessToken = authInfo?.accessToken ?: "",
                            refreshToken = authInfo?.refreshToken ?: ""
                        )
                    }

                    refreshTokens {

//                        val response = client.post()

                        val authInfo = authInfoStorage.get()
                        BearerTokens(
                            accessToken = authInfo?.accessToken ?: "",
                            refreshToken = authInfo?.refreshToken ?: ""
                        )
                    }

                }

            }

        }
    }

}