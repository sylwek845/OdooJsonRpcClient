package io.gripxtech.odoojsonrpcclient.core.persistence

import android.arch.persistence.room.TypeConverter
import com.google.gson.JsonElement
import io.gripxtech.odoojsonrpcclient.toJsonElement

class AppTypeConverters {

    @TypeConverter
    fun fromJsonElement(jsonElement: JsonElement): String = jsonElement.toString()

    @TypeConverter
    fun stringToJsonElement(string: String): JsonElement = string.toJsonElement()
}