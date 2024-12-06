package uk.ac.tees.mad.d3927542.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "destination_table")
data class Destination(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val imageUrl: String
)
