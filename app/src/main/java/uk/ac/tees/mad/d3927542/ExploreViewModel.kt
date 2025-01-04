package uk.ac.tees.mad.d3927542

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uk.ac.tees.mad.d3927542.data.Destination
import uk.ac.tees.mad.d3927542.data.DestinationRepository
import uk.ac.tees.mad.d3927542.data.Hotel
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val destinationRepository: DestinationRepository
) : ViewModel() {

    private val _destinations = MutableLiveData<List<Destination>>()
    val destinations: LiveData<List<Destination>> = _destinations

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _hotels = MutableLiveData<Hotel?>()
    val hotels: LiveData<Hotel?> = _hotels

    //Fetch destinations from firebase and fallback to Room if needed
    fun fetchDestinations() {
        _loading.value = true
        _error.value = ""
        viewModelScope.launch {

            try {
                //Attempt to fetch data from room database
                val firebaseData = destinationRepository.getAllDestinationsOnline()
                Log.d("ExploreViewModel", "Fetched destinations from Firebase: $firebaseData")
                _destinations.postValue(firebaseData)
                _loading.postValue(false)

            } catch (firebaseError: Exception) {
                Log.e(
                    "ExploreViewModel",
                    "Error fetching destinations from Firebase: ${firebaseError.message}")
                _error.postValue("Firebase error: ${firebaseError.localizedMessage}")

                //Fallback to Room

                try {
                    val roomData = destinationRepository.getAllDestinationsOffline()
                    Log.d("ExploreViewModel", "Fetched destinations from Room: $roomData")
                    _destinations.postValue(roomData)

                } catch (roomError: Exception) {
                    Log.e("ExploreViewModel", "Error fetching destinations from Room: ${roomError.message}")
                    _error.postValue("Room error: ${roomError.localizedMessage}")

                } finally {
                    _loading.postValue(false)

                }
            }
        }
    }

    //Fetch hotels for a specific destination
    fun fetchHotels(destinationId: String) {
        _loading.value = true
        _error.value = ""
        viewModelScope.launch {
            try {
                val hotelData = destinationRepository.getHotelsForDestination(destinationId)
                Log.d("ExploreViewModel", "Fetched hotels for $destinationId: $hotelData")
                _hotels.postValue(hotelData)
                _loading.postValue(false)
            } catch (exception: Exception) {
                Log.e("ExploreViewModel", "Error fetching hotels: ${exception.message}")
                _error.postValue("Error fetching hotels: ${exception.localizedMessage}")
                _loading.postValue(false)

            }
        }
    }
}
