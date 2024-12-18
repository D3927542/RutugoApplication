package uk.ac.tees.mad.d3927542.data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "destinations")
data class Destination(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val imageResId: Int?,
    val imageUrl: String?,
    val location: String?
) {
        //no-argument constructor for firebase
    constructor() : this("", "", "", null, null, null)
}
