package uk.ac.tees.mad.d3927542.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, Destination::class], version = 2) //Increment version
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun destinationDao(): DestinationDao
}