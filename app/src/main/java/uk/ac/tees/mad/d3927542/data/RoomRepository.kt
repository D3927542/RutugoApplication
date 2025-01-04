package uk.ac.tees.mad.d3927542.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomRepository @Inject constructor(private val destinationDao: DestinationDao) {

    //Get all destinations from the local Room database
    suspend fun getAllDestinations(): List<Destination> = withContext(Dispatchers.IO) {
        try {
            destinationDao.getAllDestinations()
        } catch (e: Exception) {

            emptyList()
        }
    }

    //Insert the list of destinations into the room database
    suspend fun insertDestinations(destinations: List<Destination>) = withContext(Dispatchers.IO) {
        try {
            destinationDao.insertAll(destinations)
        } catch (e: Exception) {

        }
    }
}