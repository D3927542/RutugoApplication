package uk.ac.tees.mad.d3927542

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log

fun synclocation(context: Context, location: String?) {
    if (location.isNullOrEmpty()) {
        Log.e("sync location", "Location not provided")
        return
    }

    //create the URI for Google maps
    val mapUri = Uri.parse("geo:0,0?q=${Uri.encode(location)}")
    val mapIntent = Intent(Intent.ACTION_VIEW, mapUri)
    mapIntent.setPackage("com.google.android.apps.maps")

    try {
        //try to launch google maps
        context.startActivity(mapIntent)
    } catch (e: ActivityNotFoundException) {
        //fallback to browser if google maps is not installed
        Log.e("sync location", "Google maps app not found, opening in browser: ${e.message}")
        val browserUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=${Uri.encode(location)}")
        val browserIntent = Intent(Intent.ACTION_VIEW, browserUri)
        context.startActivity(browserIntent)
    }
}