package com.tasky.agenda.data.utils

import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.os.Build
import com.tasky.agenda.domain.utils.ImageCompressor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import kotlin.math.roundToInt

class ImageCompressorImpl : ImageCompressor {

    override suspend fun compress(
        image: ByteArray,
        mimeType: String?,
        compressionThreshold: Long
    ): ByteArray {

        return withContext(Dispatchers.Default) {
            val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
            ensureActive()
            val compressFormat = when (mimeType) {
                "image/png" -> CompressFormat.PNG
                "image/jpeg" -> CompressFormat.JPEG
                "image/webp" -> if (Build.VERSION.SDK_INT >= 30) CompressFormat.WEBP_LOSSLESS else CompressFormat.WEBP
                else -> CompressFormat.JPEG
            }
            var outputBytes: ByteArray
            var quality = 100
            do {
                ByteArrayOutputStream().use { outputSteam ->
                    bitmap.compress(compressFormat, quality, outputSteam)
                    outputBytes = outputSteam.toByteArray()
                    quality -= (quality * 0.1).roundToInt()
                }
            } while (
                isActive &&
                outputBytes.size > compressionThreshold &&
                quality > 5 &&
                compressFormat != CompressFormat.PNG
            )

            outputBytes
        }
    }
}