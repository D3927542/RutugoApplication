package uk.ac.tees.mad.d3927542

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    //Create a NavController
    val navController = rememberNavController()

    //Define the NavHost and navigation routes
    NavHost(navController = navController, startDestination = "login") {
       composable("login") {
           Login(navController = navController)
       }
        composable("signup") {
            Signup(navController = navController)
        }

        composable("explore") {
            Explore(navController = navController)
        }
        composable("destination/{name}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: "Unknown"
            DestinationDetailed(name = name, navController = navController)
        }
    }
}