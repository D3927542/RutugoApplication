package uk.ac.tees.mad.d3927542

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import uk.ac.tees.mad.d3927542.data.Destination

@Composable
fun DestinationImage(destination: Destination) {
    if (destination.imageResId != null) {
        //Load local image resource
        Image(
            painter = painterResource(id = destination.imageResId),
            contentDescription = "Image of ${destination.name}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))
        )
    } else if (!destination.imageUrl.isNullOrEmpty()) {
            //Load remote image using coil
        AsyncImage(
            model = destination.imageUrl,
            contentDescription = "Image of ${destination.name}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))
        )
    }
}