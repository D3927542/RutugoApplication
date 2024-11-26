package uk.ac.tees.mad.d3927542

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.d3927542.ui.theme.RutugoApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RutugoApplicationTheme {
                //Setup Navigation
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "Login")
                {
                    composable("login") {
                        Login(navController = navController)
                    }
                    composable("signup") {
                        Signup(navController = navController)
                    }
                    composable("home") {
                        Home()
                    }

                }
            }
        }
    }
}




