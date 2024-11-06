package com.tasky.core.data.networking

import com.tasky.core.data.BuildConfig
import com.tasky.core.domain.AuthInfo
import com.tasky.core.domain.AuthInfoStorage
import com.tasky.core.domain.util.Result
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
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber

class HttpClientFactory(
    private val authInfoStorage: AuthInfoStorage,
) {
    fun build(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    json =
                    Json {
                        ignoreUnknownKeys = true
                    },
                )
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.d(message)
                    }
                }
                level = LogLevel.ALL
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
                header("x-api-key", BuildConfig.API_KEY)
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        val authInfo = authInfoStorage.get()
                        BearerTokens(
                            accessToken = authInfo?.accessToken ?: "",
                            refreshToken = authInfo?.refreshToken ?: "",
                        )
                    }

                    refreshTokens {
                        val authInfo = authInfoStorage.get()

                        val response =
                            client.post<AccessTokenRequest, AccessTokenResponse>(
                                route = "/accessToken",
                                body =
                                AccessTokenRequest(
                                    refreshToken = authInfo?.refreshToken ?: "",
                                    userId = authInfo?.userId ?: "",
                                ),
                            )

                        if (response is Result.Success) {
                            authInfoStorage.set(
                                AuthInfo(
                                    accessToken = response.data.accessToken,
                                    refreshToken = authInfo?.refreshToken ?: "",
                                    userId = authInfo?.userId ?: "",
                                    fullName = authInfo?.fullName ?: "",
                                ),
                            )

                            BearerTokens(
                                accessToken = response.data.accessToken,
                                refreshToken = authInfo?.refreshToken ?: "",
                            )
                        } else {
                            BearerTokens(
                                accessToken = "",
                                refreshToken = "",
                            )
                        }
                    }
                }
            }
        }
    }
}
