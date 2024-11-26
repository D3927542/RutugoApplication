package uk.ac.tees.mad.d3927542

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.tees.mad.d3927542.ui.theme.RutugoApplicationTheme

class SplashScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RutugoApplicationTheme {
                SplashScreenContent()
            }
        }
        //Navigate to mainActivity after a 3-second delay
        Handler(Looper.getMainLooper()).postDelayed( {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Ensures the SplashScreen is removed from the back stack
        }, 3000) // 3000 milliseconds = 3 seconds
    }
}

@Composable
fun SplashScreenContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) //plain white background
    ) {
        //Logo and App name
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
               painter = painterResource(id = R.drawable.logo_image),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(175.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            //App name
            Text(
                text = "RUTUGO",
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }
    }
}