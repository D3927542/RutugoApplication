package uk.ac.tees.mad.d3927542

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink

@Composable
fun AppNavigation() {
    //Create a NavController
    val navController = rememberNavController()

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
        composable("destination/{id}",
            deepLinks = listOf(navDeepLink { uriPattern = "android-app//androidx.navigation/destination/{id}" })
        ) { backStackEntry ->
            val destinationId = backStackEntry.arguments?.getString("id")
            DestinationDetailed(destinationId = destinationId)
        }


    }
}