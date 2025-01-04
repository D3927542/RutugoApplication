package uk.ac.tees.mad.d3927542.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [User::class, Destination::class], version = 3, exportSchema = true) //Increment version
@TypeConverters(HotelTypeConverter::class)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun destinationDao(): DestinationDao
}