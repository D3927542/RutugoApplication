package uk.ac.tees.mad.d3927542

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink

@Composable
fun AppNavigation() {
    //Create a NavController
    val navController = rememberNavController()

    //Log the registered destinations
    LaunchedEffect(navController) {
        navController.graph.forEach { destination ->
            Log.d("NavigationGraph", "Registered destination: ${destination.route}")

        }
    }

    //Define the NavHost and navigation routes
    NavHost(navController = navController, startDestination = "login") {

        //Login route
       composable("login") {
           Login(navController = navController)
       }
        //signup route
        composable("signup") {
            Signup(navController = navController)
        }
          //Explore route
        composable("explore") {
            Explore(navController = navController)
        }

        //Destination detailed route
        composable("destination/{name}",
            deepLinks = listOf(navDeepLink { uriPattern = "android-app://uk.ac.tees.mad.d3927542/destination/{name?}" })
        ) { backStackEntry ->
            val destinationName = backStackEntry.arguments?.getString("name")
            DestinationDetailed(destinationName = destinationName)
        }


    }
}