package uk.ac.tees.mad.d3927542

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel


class HorseInfoViewModel : ViewModel() {

    // The UI state.
    var horseInfoList = mutableStateListOf<String>()

    fun fetch(
        username: String,
        password: String,
        onSuccess: (List<String>) -> Unit = {},
        onFailure: (String?) -> Unit = {}) {

        ApiCall(username, password)
            .getHorseList({
                horseInfoList.addAll(it)

                onSuccess(horseInfoList)

                Log.d("HorseInfoViewModel", it.toString())
            },
                {
                    Log.d("HorseInfoViewModel", "Error! $it")
                    onFailure(it)
                })
    }
}