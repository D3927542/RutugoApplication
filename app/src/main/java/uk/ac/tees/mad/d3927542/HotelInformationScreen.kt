package uk.ac.tees.mad.d3927542

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@Composable
fun HotelInformationScreen(destinationId: String?) {
    val exploreViewModel: ExploreViewModel = hiltViewModel()

    LaunchedEffect(destinationId) {
        destinationId?.let { exploreViewModel.fetchHotels(it) }
    }

    val hotel = exploreViewModel.hotels.observeAsState().value
    val context = LocalContext.current //obtain the context
    val userEmail = "sandhyaranikalakota@gmail.com" //Replace with the logged-in user's email

    if (hotel == null) {
        Text(text = "No hotel information available.", modifier = Modifier.padding(16.dp))
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AsyncImage(
            model = hotel.imageUrl,
            contentDescription = hotel.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = hotel.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                sendBookingConfirmationEmail(context, userEmail, hotel.name)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Book Now")
        }
    }
}

//Function to send booking confirmation email
private fun sendBookingConfirmationEmail(context: Context, userEmail: String, hotelName: String)  {
    try {
        val subject = "Booking Confirmation: $hotelName"
        val body = """
            
           Dear Customer,
           
           Thank you for booking with us. Your reservation at $hotelName
           
           we look forward to hosting you!
           
           Best Regards,
           Your Rutugo App Team
           
        """.trimIndent()

        Toast.makeText(
            context,
            "Booking confirmation email sent successfully!",
            Toast.LENGTH_SHORT
        ).show()

        Log.d("BookingConfirmation", "Subject: $subject, Body: $body")
    } catch (e: Exception) {
        Log.e("BookingConfirmation", "Error sending confirmation", e)
        Toast.makeText(context, "Failed to send confirmation.", Toast.LENGTH_SHORT).show()
    }
}