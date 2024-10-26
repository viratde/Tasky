package com.tasky.agenda.domain.utils

interface ImageCompressor {

    suspend fun compress(
        image: ByteArray,
        mimeType:String?,
        compressionThreshold: Long
    ) : ByteArray

}