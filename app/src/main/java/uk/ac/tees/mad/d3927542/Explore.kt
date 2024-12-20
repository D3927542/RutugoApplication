package uk.ac.tees.mad.d3927542

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import uk.ac.tees.mad.d3927542.data.Destination


/**
 * Composable function that represents the Explore page, displaying a list of destinations
 * fetched from firebase firestore in a scrollable list.
 */
@Composable
fun Explore(navController: NavController) {
    val exploreViewModel: ExploreViewModel = hiltViewModel()

    //Search Query state
    val searchQuery = remember { mutableStateOf("") }

    //Observe destinations from ViewModel
    val destinations by exploreViewModel.destinations.observeAsState(emptyList())
    val loading by exploreViewModel.loading.observeAsState(false)
    val error by exploreViewModel.error.observeAsState("")

    //Fetch destinations on launch
    LaunchedEffect(Unit) {
        exploreViewModel.fetchDestinations()
    }

    //Page layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        //Title
        Text(
            text = "Explore",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        //search bar
        OutlinedTextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            label = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        //Apply search filter
        val filteredDestinations = destinations.filter {
            it.name.contains(searchQuery.value, ignoreCase = true) ||
                    it.description.contains(searchQuery.value, ignoreCase = true)
        }

        Log.d("Explore", "Search Query: ${searchQuery.value}")
        Log.d("Explore", "Filtered Destinations: $filteredDestinations")
        //Show loading spinner while fetching data
        when {
            loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            error.isNotEmpty() -> Text(
                text = error,
                color = androidx.compose.ui.graphics.Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            filteredDestinations.isEmpty() -> Text(
                text = "No destinations found.",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            else -> {
                //Display the list of destinations in a scrollable LazyColumn.
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    //Create a list item for each destination in the list.
                    items(filteredDestinations) { destination ->
                        Log.d("Explore", "Destination: $destination")
                        DestinationCard(destination = destination) {
                            navController.navigate("destination/${destination.id}")
                            Log.d("Navigation", "Navigating to: destination/${destination.id}")
                        }
                    }

                }
            }
        }
    }
}

/**
 * Composable function represents a card displaying a single destination's image,
 * name, and description
 */
@Composable
fun DestinationCard(destination: Destination, onClick: () -> Unit) {
    //A card is a container with rounded corners and elevation.
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { onClick() }, //Click action to navigate to detailed destination page
        shape = RoundedCornerShape(16.dp)
    ) {
       Column(
           modifier = Modifier.fillMaxSize()
       ) {
           //Display the destination image
           Image(
               painter = rememberAsyncImagePainter( model = destination.imageUrl), //Load image from url
               contentDescription = "Destination Image",
               contentScale = ContentScale.Crop,
               modifier = Modifier
                   .fillMaxWidth()
                   .height(120.dp)
           )

           Spacer(modifier = Modifier.height(8.dp))

           Text(
               text = destination.name,
               fontSize = 20.sp,
               fontWeight = FontWeight.Bold,
               modifier = Modifier.padding(horizontal = 8.dp)
           )

           Text(
               text = destination.description,
               fontSize = 14.sp,
               modifier = Modifier.padding(horizontal = 8.dp)
           )
       }
    }
}
