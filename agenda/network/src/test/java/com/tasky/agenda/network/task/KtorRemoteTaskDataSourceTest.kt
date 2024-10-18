package com.tasky.agenda.network.task

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import com.tasky.agenda.domain.model.Task
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.Result
import com.tasky.test_utils.agenda.domain.fakeTask
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondBadRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestData
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class KtorRemoteTaskDataSourceTest {


    private lateinit var httpClient: HttpClient
    private lateinit var ktorRemoteTaskDataSource: KtorRemoteTaskDataSource
    private lateinit var requests: MutableList<HttpRequestData>
    private lateinit var fakeTask: Task
    private var shouldReturnErrorInResponse: Boolean = false

    @BeforeEach
    fun setup() {
        requests = mutableListOf()
        fakeTask = fakeTask()
        httpClient = HttpClient(MockEngine) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
                level = LogLevel.ALL
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
            engine {

                addHandler { request ->
                    requests.add(request)
                    when (request.url.encodedPath) {
                        "/task" -> {
                            when (request.method) {
                                HttpMethod.Post -> {
                                    if (shouldReturnErrorInResponse) {
                                        respond(
                                            content = "",
                                            status = HttpStatusCode.Conflict,
                                            headersOf(
                                                "Content-Type" to listOf(ContentType.Application.Json.toString())
                                            )
                                        )
                                    } else {
                                        respond(
                                            content = "",
                                            status = HttpStatusCode.OK,
                                            headers = headersOf(
                                                "Content-Type" to listOf(ContentType.Application.Json.toString())
                                            )
                                        )
                                    }
                                }

                                HttpMethod.Get -> {
                                    if (shouldReturnErrorInResponse) {
                                        respond(
                                            content = "",
                                            status = HttpStatusCode.Conflict,
                                            headersOf(
                                                "Content-Type" to listOf(ContentType.Application.Json.toString())
                                            )
                                        )
                                    } else {
                                        respond(
                                            content = Json.encodeToString(fakeTask.toTaskDto()),
                                            status = HttpStatusCode.OK,
                                            headers = headersOf(
                                                "Content-Type" to listOf(ContentType.Application.Json.toString())
                                            )
                                        )
                                    }
                                }

                                HttpMethod.Put -> {
                                    if (shouldReturnErrorInResponse) {
                                        respond(
                                            content = "",
                                            status = HttpStatusCode.Conflict,
                                            headersOf(
                                                "Content-Type" to listOf(ContentType.Application.Json.toString())
                                            )
                                        )
                                    } else {
                                        respond(
                                            content = "",
                                            status = HttpStatusCode.OK,
                                            headers = headersOf(
                                                "Content-Type" to listOf(ContentType.Application.Json.toString())
                                            )
                                        )
                                    }
                                }

                                HttpMethod.Delete -> {
                                    if (shouldReturnErrorInResponse) {
                                        respond(
                                            content = "",
                                            status = HttpStatusCode.Conflict,
                                            headersOf(
                                                "Content-Type" to listOf(ContentType.Application.Json.toString())
                                            )
                                        )
                                    } else {
                                        respond(
                                            content = "",
                                            status = HttpStatusCode.OK,
                                            headers = headersOf(
                                                "Content-Type" to listOf(ContentType.Application.Json.toString())
                                            )
                                        )
                                    }
                                }

                                else -> respondBadRequest()
                            }
                        }

                        else -> respondBadRequest()
                    }
                }
            }
        }
        ktorRemoteTaskDataSource = KtorRemoteTaskDataSource(
            httpClient = httpClient
        )
        shouldReturnErrorInResponse = false
    }


    @Test
    fun test_success_cases_for_all_requests() = runTest {

        assertThat(ktorRemoteTaskDataSource.get(fakeTask.id))
            .isEqualTo(Result.Success(fakeTask))
        assertThat(
            requests.find { req ->
                req.url.encodedPath == "/task" && req.method == HttpMethod.Get && req.url.encodedQuery == "taskId=${fakeTask.id}"
            }
        ).isNotNull()

        assertThat(ktorRemoteTaskDataSource.create(fakeTask))
            .isEqualTo(Result.Success(Unit))
        assertThat(
            requests.find { req ->
                req.url.encodedPath == "/task" && req.method == HttpMethod.Post
            }
        ).isNotNull()

        assertThat(ktorRemoteTaskDataSource.update(fakeTask))
            .isEqualTo(Result.Success(Unit))
        assertThat(
            requests.find { req ->
                req.url.encodedPath == "/task" && req.method == HttpMethod.Put
            }
        ).isNotNull()

        assertThat(ktorRemoteTaskDataSource.delete(fakeTask.id))
            .isEqualTo(Result.Success(Unit))
        assertThat(
            requests.find { req ->
                req.url.encodedPath == "/task" && req.method == HttpMethod.Delete
            }
        ).isNotNull()


    }

    @Test
    fun test_failure_cases_for_all_requests() = runTest {

        shouldReturnErrorInResponse = true

        assertThat(ktorRemoteTaskDataSource.get(fakeTask.id))
            .isEqualTo(Result.Error(DataError.Network.CONFLICT))
        assertThat(
            requests.find { req ->
                req.url.encodedPath == "/task" && req.method == HttpMethod.Get && req.url.encodedQuery == "taskId=${fakeTask.id}"
            }
        ).isNotNull()

        assertThat(ktorRemoteTaskDataSource.create(fakeTask))
            .isEqualTo(Result.Error(DataError.Network.CONFLICT))
        assertThat(
            requests.find { req ->
                req.url.encodedPath == "/task" && req.method == HttpMethod.Post
            }
        ).isNotNull()

        assertThat(ktorRemoteTaskDataSource.update(fakeTask))
            .isEqualTo(Result.Error(DataError.Network.CONFLICT))
        assertThat(
            requests.find { req ->
                req.url.encodedPath == "/task" && req.method == HttpMethod.Put
            }
        ).isNotNull()

        assertThat(ktorRemoteTaskDataSource.delete(fakeTask.id))
            .isEqualTo(Result.Error(DataError.Network.CONFLICT))
        assertThat(
            requests.find { req ->
                req.url.encodedPath == "/task" && req.method == HttpMethod.Delete
            }
        ).isNotNull()


    }

}