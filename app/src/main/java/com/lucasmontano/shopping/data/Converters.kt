package com.lucasmontano.shopping.data

import androidx.room.TypeConverter
import com.lucasmontano.shopping.data.entities.ProductEntity
import org.json.JSONObject
import java.util.*

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {
    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun datestampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }

    @TypeConverter
    fun priceToJsonStringify(value: ProductEntity.Price): String = JSONObject().apply {
        put("value", value.value)
        put("currency", value.currency)
    }.toString()

    @TypeConverter
    fun jsonStringifyToPrice(value: String): ProductEntity.Price {
        val priceJson = JSONObject(value)
        return ProductEntity.Price(
            value = priceJson.getDouble("value"),
            currency = priceJson.getString("currency")
        )
    }
}
