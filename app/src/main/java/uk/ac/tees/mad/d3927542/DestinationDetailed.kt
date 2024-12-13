package uk.ac.tees.mad.d3927542

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DestinationDetailed(destinationId: String?) {
    val exploreViewModel: ExploreViewModel = hiltViewModel()
    val destination = exploreViewModel.destinations.value?.find { it.id == destinationId }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = destination?.name ?: "Destination Details",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)

        )
        Text(
            text = destination?.description ?: "Description not available",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

    }
}