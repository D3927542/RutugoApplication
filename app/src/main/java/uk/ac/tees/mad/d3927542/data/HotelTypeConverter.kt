package uk.ac.tees.mad.d3927542.data

import androidx.room.TypeConverter
import com.google.gson.Gson

class HotelTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromHotel(hotel: Hotel?): String? {
        return hotel?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toHotel(hotelJson: String?): Hotel? {
        return hotelJson?.let { Gson().fromJson(it, Hotel::class.java) }
    }
}