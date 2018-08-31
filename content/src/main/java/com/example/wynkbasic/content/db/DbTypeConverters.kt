package com.example.wynkbasic.content.db

import android.arch.persistence.room.TypeConverter
import org.json.JSONObject
import java.nio.charset.Charset

class DbTypeConverters {

    class JsonObjectTypeConverter {
        @TypeConverter
        fun byteArrayToJson(data: ByteArray): JSONObject {
            return JSONObject(String(data, Charset.forName("UTF-8")))
        }

        @TypeConverter
        fun jsonToByteArray(jsonObject: JSONObject): ByteArray {
            return jsonObject.toString().toByteArray(Charset.forName("UTF-8"))
        }
    }
}