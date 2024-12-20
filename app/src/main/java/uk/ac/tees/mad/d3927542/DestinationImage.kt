package uk.ac.tees.mad.d3927542

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import uk.ac.tees.mad.d3927542.data.Destination

@Composable
fun DestinationImage(destination: Destination) {
    //Load remote image using coil
    val imageUrl = destination.imageUrl ?: destination.imageResId?.toString()
    if (!imageUrl.isNullOrEmpty()) {
        //Load local image resource
        AsyncImage(
            model = imageUrl,
            contentDescription = "Image of ${destination.name}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))
        )

    } else {

        Text(text = "No Image Available", modifier = Modifier.fillMaxWidth())
    }
}