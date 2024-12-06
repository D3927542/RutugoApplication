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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.customview.widget.ExploreByTouchHelper
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.firestore.FirebaseFirestore
import uk.ac.tees.mad.d3927542.data.Destination

/**
 * Composable function that represents the Explore page, displaying a list of destinations
 * fetched from firebase firestore in a scrollable list.
 */
@Composable
fun Explore(navController: NavController) {
    //Mutable list to store the destination fetched from firestore.
    val destinations = remember {
        mutableStateListOf<Destination>()
    }
    //Search query state
    var searchQuery by remember {
        mutableStateOf("")
    }
    //Get a reference to the firestore database.
    val db = FirebaseFirestore.getInstance()

    //LaunchedEffect is a side-effect that runs once when the composable is first composed.
    //Here, it is used to fetch data from firestore.

    LaunchedEffect(Unit) {
        //Fetch data from "destinations" collection in firestore.

        db.collection("destinations")
            .get()
            .addOnSuccessListener { result ->
                destinations.clear()
                //iterate through each document in the result and convert it to a destination object.
                for (document in result) {
                      val destination = document.toObject(Destination::class.java)
                    //Add the destination to the mutable list to update the UI.
                    destinations.add(destination)
                }
            }
            .addOnFailureListener{ exception ->
                //Log an error message if the data fetch fails.
                Log.e("FirestoreError", "Error fetching data", exception)
            }
    }

    //Filtered destinations based on the search query
    val filteredDestinations = destinations.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    //Page layout
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        //Title
        Text(
            text = "Explore",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        //search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )
    }

    //Display the list of destinations in a scrollable LazyColumn.
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        //Create a list item for each destination in the list.
        items(filteredDestinations.size) { index ->
            DestinationCard(destination = filteredDestinations[index],
                onClick = {
                    navController.navigate("destination/${filteredDestinations[index].name}")
                }
            )
            
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
               painter = rememberAsyncImagePainter(destination.imageUrl), //Load image from url
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
