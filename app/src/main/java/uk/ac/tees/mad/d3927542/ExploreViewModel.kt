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
}
