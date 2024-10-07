package com.tasky.auth.data

import com.tasky.test_utils.auth.data.FakeAuthInfoStorage
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import org.junit.Before

class AuthRepositoryImplTest {

    private lateinit var httpClient: HttpClient
    private lateinit var authInfoStorage: FakeAuthInfoStorage


    @Before
    fun setUp() {
        httpClient = HttpClient(MockEngine) {
            engine {
                addHandler { request ->
                    if (request.url.encodedPath == "/login") {
                        respond(
                            content = "{}",
                            status = HttpStatusCode.OK,
                            headers = headersOf(
                                "Content-Type" to listOf(ContentType.Application.Json.toString())
                            )
                        )
                    } else if (request.url.encodedPath == "/register") {
                        respond(
                            content = "",
                            status = HttpStatusCode.OK,
                            headers = headersOf(
                                "Content-Type" to listOf(ContentType.Application.Json.toString())
                            )
                        )
                    } else {
                        respondError(HttpStatusCode.NotFound)
                    }
                }
            }
        }
    }

}