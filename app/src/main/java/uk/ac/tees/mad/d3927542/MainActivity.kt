package uk.ac.tees.mad.d3927542

import android.annotation.SuppressLint
import uk.ac.tees.mad.d3927542.ui.theme.RutugoApplicationTheme
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RutugoApplicationTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                Text("Login",
                                    style = MaterialTheme.typography.headlineLarge.copy(
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )

                                }
                            }
                        )
                    }
                ) {
                    AppScreen()
                }
            }
        }
    }
}

@Composable
fun AppScreen() {
    val horseInfoViewModel: HorseInfoViewModel = viewModel()
    val userCredentialsViewModel: UserCredentialsViewModel = viewModel()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF)),  // Full screen background color
        contentAlignment = Alignment.Center // Centers content in the middle of the screen
    ) {
       Column(horizontalAlignment =  Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.spacedBy(8.dp),
           modifier = Modifier.fillMaxSize()
       ) {
            UserCredentials()

            Button(
                onClick = {
                    horseInfoViewModel.fetch(
                        userCredentialsViewModel.username,
                        userCredentialsViewModel.password,
                        {
                            userCredentialsViewModel.reset()
                            Toast.makeText(context, "Login Successfully", Toast.LENGTH_LONG).show()
                        },
                        { errorMsg ->
                            Toast.makeText(context, "Login Failed: $errorMsg", Toast.LENGTH_LONG).show()
                        }
                    )
                },
                enabled = userCredentialsViewModel.areCredentialsPresent(),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
            }
            HorseData(horseInfoViewModel)
        }
    }
}

@Composable
fun HorseData(
    horseInfoViewModel: HorseInfoViewModel,
    modifier: Modifier = Modifier,
) {
    if (horseInfoViewModel.horseInfoList.isNotEmpty()) {
        horseInfoViewModel.horseInfoList.forEach {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .then(modifier)
            ) {
                Text(text = it, modifier = Modifier.padding(5.dp))
            }
        }
    } else {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(70.dp)
                .then(modifier)
        ){
            Text(text = "Don't have an account? Signup")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppScreenPreview() {
    RutugoApplicationTheme {
        AppScreen()
    }
}
