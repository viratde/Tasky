package com.tasky.auth.data

import com.tasky.auth.domain.AuthRepository
import com.tasky.core.data.networking.post
import com.tasky.core.domain.AuthInfo
import com.tasky.core.domain.AuthInfoStorage
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result
import com.tasky.core.domain.util.asEmptyDataResult
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val authInfoStorage: AuthInfoStorage
) : AuthRepository {

    override suspend fun register(
        fullName: String,
        email: String,
        password: String
    ): EmptyDataResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(
                fullName = fullName,
                email = email,
                password = password
            )
        )
    }

    override suspend fun login(
        email: String,
        password: String
    ): EmptyDataResult<DataError.Network> {
        val response = httpClient.post<LoginRequest, LoginResponse>(
            route = "/login",
            body = LoginRequest(
                email = email,
                password = password
            )
        )

        if (response is Result.Success) {
            authInfoStorage.set(
                AuthInfo(
                    accessToken = response.data.accessToken,
                    refreshToken = response.data.refreshToken,
                    fullName = response.data.fullName,
                    userId = response.data.userId
                )
            )
        }

        return response.asEmptyDataResult()

    }

}