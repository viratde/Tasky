package com.tasky.agenda.data.converters

import androidx.room.TypeConverter
import com.tasky.agenda.data.utils.AttendeeEntity
import com.tasky.agenda.data.utils.PhotoEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class EventTypeConverters {

    @TypeConverter
    fun attendeeListToString(data: List<AttendeeEntity>): String {
        return Json.encodeToString(data)
    }

    @TypeConverter
    fun stringToListAttendee(data: String): List<AttendeeEntity> {
        return Json.decodeFromString(data)
    }

    @TypeConverter
    fun photosListToString(data: List<PhotoEntity>): String {
        return Json.encodeToString(data)
    }

    @TypeConverter
    fun stringToPhotosList(data: String): List<PhotoEntity> {
        return Json.decodeFromString(data)
    }

}