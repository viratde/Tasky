package com.tasky.test_utils.auth.data

import com.tasky.core.domain.AuthInfo
import com.tasky.core.domain.AuthInfoStorage

class FakeAuthInfoStorage : AuthInfoStorage {
    private var authInfo: AuthInfo? = null

    override suspend fun set(authInfo: AuthInfo?) {
        this.authInfo = authInfo
    }

    override suspend fun get(): AuthInfo? {
        return this.authInfo
    }

}