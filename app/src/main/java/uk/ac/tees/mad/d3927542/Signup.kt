package uk.ac.tees.mad.d3927542

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import uk.ac.tees.mad.d3927542.data.User
import uk.ac.tees.mad.d3927542.data.UserDatabase

@Composable
fun Signup(navController: NavController) {
    var fullName by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var emailError by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    //Initialize Room Database
    val userDb = Room.databaseBuilder(
        context.applicationContext,
        UserDatabase::class.java,
        "user_database"
    ).build()

    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color(0xFFF5F5F5)) //Light Gray Background
    ) {

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Heading at the Top
            Text("Sign Up",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.Start),
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    //combined multiple check to ensure the email.
                    emailError = when {
                    !it.contains("@") || !it.contains(".") || !it.endsWith("@gmail.com") ->
                        "Please enter a valid email address"
                    else -> "" //No error
                }
                                },
                label = { Text("Email") },
                isError = emailError.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            //If the email is invalid, the error text is displayed in below the input field
            if (emailError.isNotEmpty()) {
                Text(
                    emailError,
                    color = Color.Red,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 16.dp, top = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            PasswordField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            PasswordField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirm Password",
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                if (password == confirmPassword) {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                //Firebase Registration Successful
                                val userEmail = email
                                val userName = fullName

                                //Save user data locally using Room
                                (context as? ComponentActivity)?.lifecycleScope?.launch {
                                    userDb.userDao().insertUser(User(email = userEmail, name = userName))
                                }
                                Toast.makeText(
                                    context,
                                    "Account Created Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.navigate("login")
                            } else {
                                //Firebase Registration Failed
                                Toast.makeText(
                                    context,
                                    "Signup Failed: ${task.exception?.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(context, "Password do not match", Toast.LENGTH_SHORT).show()
                }
            },
                //The sign-up button remains disabled until the email passes validation.
               enabled = emailError.isEmpty(),
                modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp)
            ) {
                Text("Sign Up")
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { navController.navigate("Login") }) {
                Text("Already have an account? Login", color = Color.Blue)
            }
        }
    }
}