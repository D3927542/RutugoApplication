package uk.ac.tees.mad.d3927542.data

import android.util.Log
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
            destination?.let {
                it.id = document.id
                destinations.add(it)
            }
        }

        //save data to Room for offline access
        destinationDao.insertAll(destinations)
        return destinations
    }

    //Fetch a single destination by id from firestore(online)
    suspend fun getDestinationByIdOnline(id: String): Destination? {
        val document = firestore.collection("destinations").document(id).get().await()
        return if (document.exists()) {
            document.toObject(Destination::class.java)?.apply {
                this.id = document.id
            }
        } else {
            null
        }
    }

    //Fetch  a single destination by id from Room(offline)
    suspend fun getDestinationByIdOffline(id: String): Destination? {
        return destinationDao.getDestinationById(id)
    }

    //Fetch hotels for a specific destinations from firestore
    suspend fun getHotelsForDestination(destinationId: String): Hotel? {
        val document = firestore.collection("destinations").document(destinationId).get().await()

        //Fetch hotels as a map from Firestore
        val hotelData = document.get("hotels") as? Map<String, Any> ?: return null
        Log.d("DestinationRepository", "Fetched hotels data: $hotelData")

        return try {
                Hotel(
                    name = hotelData["name"] as? String ?: "Unknown",
                    imageUrl = hotelData["imageUrl"] as? String ?: "",
                )
            } catch (e: Exception) {
                //Log error and return a default Hotel object
                Log.e("DestinationRepository", "Error mapping hotel: ${e.message}")
                null
            }
        }
    }