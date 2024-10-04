package com.tasky.core.data.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.tasky.core.domain.AuthInfo
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AuthInfoStorageImplTest {


    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var authInfoStorageImpl: AuthInfoStorageImpl

    @Before
    fun setUp() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        sharedPreferences = context.getSharedPreferences("test_prefs", Context.MODE_PRIVATE)
        authInfoStorageImpl = AuthInfoStorageImpl(
            sharedPreferences = sharedPreferences
        )

    }

    @Test
    fun save_and_read_auth_info_testing() = runTest {
        val authInfo = AuthInfo(
            accessToken = "test_access_token",
            refreshToken = "test_refresh_token",
            fullName = "testKumar",
            userId = "testUserID"
        )
        authInfoStorageImpl.set(
            authInfo
        )

        assertk.assertThat(authInfoStorageImpl.get()).isEqualTo(authInfo)

        authInfoStorageImpl.set(
            null
        )

        assertk.assertThat(authInfoStorageImpl.get()).isNull()
    }


}