package uk.ac.tees.mad.d3927542.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepository @Inject constructor(private val firestore: FirebaseFirestore) {

    //Fetch destinations from firebase Firestore
    suspend fun getDestinationsFromFirestore(): List<Destination> {
        val destinations = mutableListOf<Destination>()
        val result = firestore.collection("destinations").get().await()

        for (document in result.documents) {
            val destination = document.toObject(Destination::class.java)
            destination?.let {
                //Set the Firestore document Id as the 'id' of the destination object
                it.id = document.id
                destinations.add(it)
            }
        }

        return destinations
    }
}