package uk.ac.tees.mad.d3927542.data

import javax.inject.Inject

class RoomRepository @Inject constructor(private val destinationDao: DestinationDao) {

    //Get all destinations from the local Room database
    suspend fun getAllDestinations(): List<Destination> {
        return destinationDao.getAllDestinations()
    }

    //Insert the list of destinations into the room database
    suspend fun insertDestinations(destinations: List<Destination>) {
        destinationDao.insertAll(destinations)
    }
}