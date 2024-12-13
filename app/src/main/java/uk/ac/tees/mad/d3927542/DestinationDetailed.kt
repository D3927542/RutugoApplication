package uk.ac.tees.mad.d3927542

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import uk.ac.tees.mad.d3927542.DestinationImage

@Composable
fun DestinationDetailed(destinationName: String?) {
    val exploreViewModel: ExploreViewModel = hiltViewModel()
    val destination = exploreViewModel.destinations.value?.find { it.name == destinationName }

    if (destination == null) {
        Text(text = "Invalid Destination or not found.", modifier = Modifier.padding(16.dp))
        return
    }

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
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        //Suggested travel data(Calendar UI)
        TravelDatePicker()

        //Location information
        Button(
            onClick = {
                //Sync with the location or open a map
                syncLocation(destination.location)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Check location")
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

fun syncLocation(location: String?) {
    //Logic to sync with a map service
    println("Syncing location for $location")
}