package com.tasky.core.data.auth

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.tasky.core.domain.AuthInfo
import com.tasky.core.domain.AuthInfoStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AuthInfoStorageImpl(
    private val sharedPreferences: SharedPreferences,
) : AuthInfoStorage {
    @SuppressLint("ApplySharedPref")
    override suspend fun set(authInfo: AuthInfo?) {
        return withContext(Dispatchers.IO) {
            if (authInfo == null) {
                sharedPreferences.edit().remove(AUTH_INFO_KEY).commit()
            } else {
                val json = Json.encodeToString(authInfo.toAuthInfoSerializable())
                sharedPreferences.edit().putString(AUTH_INFO_KEY, json).commit()
            }
        }
    }

    override suspend fun get(): AuthInfo? {
        return withContext(Dispatchers.IO) {
            val json = sharedPreferences.getString(AUTH_INFO_KEY, null)

            json?.let {
                Json.decodeFromString<AuthInfoSerializable>(it).toAuthInfo()
            }
        }
    }

    companion object {
        const val AUTH_INFO_KEY = "AUTH_INFO_KEY"
    }
}
