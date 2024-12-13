package uk.ac.tees.mad.d3927542

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import uk.ac.tees.mad.d3927542.ui.theme.RutugoApplicationTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RutugoApplicationTheme {
                AppNavigation()
            }
        }
    }
}




