package uk.ac.tees.mad.d3927542

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCredentials(
    modifier: Modifier = Modifier
) {
    val userCredentialViewModel: UserCredentialsViewModel = viewModel()

    Spacer(modifier = Modifier.height(70.dp))
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp, vertical = 20.dp).then(modifier)) {
        OutlinedTextField(
            value = userCredentialViewModel.username,
            onValueChange = { userCredentialViewModel.username = it }, // Update the viewmodel ...
            label = { Text(text = LocalContext.current.getString(R.string.username_textfield)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        PasswordField(
            userCredentialViewModel.password,
           onPasswordChange =  { userCredentialViewModel.password = it },
           modifier = Modifier.fillMaxWidth() // Update the viewmodel ...
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PasswordField(
    password: String, // Password string being passed down the UI
    onPasswordChange: (String) -> Unit, // Callback when the password is updated
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val passwordPlaceholderText = context.getString(R.string.password_textfield)


    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(passwordPlaceholderText) },
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible)
                            Icons.Filled.Visibility
                        else
                            Icons.Filled.VisibilityOff

            // Localized description for accessibility services
            val description =   if (passwordVisible)
                                    context.getString(R.string.hide_password)
                                else
                                    context.getString(R.string.show_password)

            // Toggle button to hide or display password
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector  = image, description)
            }
        },
        modifier = modifier
    )
}