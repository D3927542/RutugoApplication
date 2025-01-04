package uk.ac.tees.mad.d3927542

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import uk.ac.tees.mad.d3927542.data.Hotel

@Composable
fun DestinationDetailed(destinationId: String?, navigateToHotels: (String) -> Unit
) {
    Log.d("DestinationDetailed", "Received destinationId: $destinationId")

    val exploreViewModel: ExploreViewModel = hiltViewModel()

    if (exploreViewModel.destinations.value.isNullOrEmpty()) {
        exploreViewModel.fetchDestinations()
    }

    //Fetch hotels for the destinations
    LaunchedEffect(destinationId) {
        destinationId?.let { exploreViewModel.fetchHotels(it)
        Log.d("DestinationDetailed", "Fetched hotels: ${exploreViewModel.hotels.value}")
        }
    }

    val destinations = exploreViewModel.destinations.observeAsState(emptyList()).value
    val hotel = exploreViewModel.hotels.observeAsState().value
    Log.d("DestinationDetailed", "Fetched hotel: $hotel")

    Log.d("DestinationDetailed", "Available destinations: $destinations")

    if (destinations.isNullOrEmpty()) {
        Log.d("DestinationDetailed", "No destinations available.")
        Text(text = "No destinations available", modifier = Modifier.padding(16.dp))
        return
    }

    //Find the destinations by id or name
    val destination = destinations.find { it.id == destinationId }

    //if the destination is not found, show an error message
    if (destination == null) {
        Log.d("DestinationDetailed", "Destination not found for id: $destinationId")
        Text(text = "Destination not found.", modifier = Modifier.padding(16.dp))
        return

    }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        //Description image
        DestinationImage(destination = destination)

        //Destination Name
        Text(
            text = destination.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)

        )

        //Additional details
        Text(
            text = "Description: ${destination.description}",
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        //Suggested travel data(Calendar UI)
        TravelDatePicker()

        //Button to open location in google maps
        Button(
            onClick = {
                if (!destination.location.isNullOrEmpty()) {
                    synclocation(context, destination.location)
                } else {
                    Log.e("DestinationDetailed", "Location not available")
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Check location")
        }

        Spacer(modifier = Modifier.height(16.dp))

        //hotels Section
        Text(

            text = "Nearby Hotels",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
                .clickable { navigateToHotels(destinationId ?: "") }
        )
        if (hotel != null) {

            HotelCard(hotel = hotel)
        }else {
            Text(
                text = "No hotels available for this destination.",
                modifier = Modifier.padding(8.dp)
            )
        }
    }

}

@Composable
fun HotelCard(hotel: Hotel) {
    val name = hotel.name
    val imageUrl = hotel.imageUrl

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(200.dp),
        elevation = CardDefaults.elevatedCardElevation(2.dp)
    ) {
        Column (modifier = Modifier.padding(16.dp)) {
            if (!imageUrl.isNullOrEmpty()) {
                AsyncImage(
                   model = imageUrl,
                    contentDescription = name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
            } else {
                Text(
                    text = "Image not available",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Text(text = name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp))
        }
    }
}

@Composable
fun TravelDatePicker() {
    //This could be use a library or a custom date picker implementation
    Text(
         text = "Recommended Travel Date: Choose one below",
         fontSize = 16.sp,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    //Example: Add date options
    Column {
        Text(text = "Option 1: Dec 25, 2024", modifier = Modifier.padding(4.dp))
        Text(text = "Option 2: Jan 1, 2025", modifier = Modifier.padding(4.dp))
    }
}
