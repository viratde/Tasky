package com.tasky.core.presentation.ui

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.InputStream

suspend fun Uri.toByteArray(context: Context): ByteArray? {
    return withContext(Dispatchers.IO) {
        val contentResolver: ContentResolver = context.contentResolver
        val inputStream: InputStream? = contentResolver.openInputStream(this@toByteArray)
        val byteArrayOutputStream = ByteArrayOutputStream()

        return@withContext inputStream?.use {
            val buffer = ByteArray(1024)
            var length: Int = 0
            while (isActive && inputStream.read(buffer).also { length = it } != -1) {
                byteArrayOutputStream.write(buffer, 0, length)
            }

            byteArrayOutputStream.toByteArray()
        }
    }
}
