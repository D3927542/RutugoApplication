package uk.ac.tees.mad.d3927542.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DestinationRepository @Inject constructor(
    private val destinationDao: DestinationDao,
    private val firestore: FirebaseFirestore
) {
    //Fetch destinations from Room (offline)
    suspend fun getAllDestinationsOffline(): List<Destination> {
        return destinationDao.getAllDestinations()
    }

    //Fetch destinations from Firebase(online)
    suspend fun getAllDestinationsOnline(): List<Destination> {
        val destinations = mutableListOf<Destination>()
        val result = firestore.collection("destinations").get().await()

        for (document in result.documents) {
            val destination = document.toObject(Destination::class.java)
            destination?.let { destinations.add(it) }
        }

        //save data to Room for offline access
        destinationDao.insertAll(destinations)
        return destinations
    }
}