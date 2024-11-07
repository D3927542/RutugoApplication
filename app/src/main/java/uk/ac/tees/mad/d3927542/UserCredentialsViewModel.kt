package uk.ac.tees.mad.d3927542

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class UserCredentialsViewModel : ViewModel() {

    var username by mutableStateOf("") // Delegate
    var password by mutableStateOf("") // Delegate
    fun areCredentialsPresent(): Boolean {
        return username.isNotEmpty() && password.isNotEmpty()
    }

    fun reset() {
        username = ""
        password = ""
    }
}