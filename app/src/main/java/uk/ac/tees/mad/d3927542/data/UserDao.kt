package uk.ac.tees.mad.d3927542.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    //User-related methods
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user_table WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?
}
@Dao
interface DestinationDao {

        //Destination-related methods
        @Insert
        suspend fun insertDestination(destination: Destination)

        @Query("SELECT * FROM destination_table")
        suspend fun getAllDestinations(): List<Destination>

        @Query("DELETE FROM destination_table WHERE id = :destinationId")
        suspend fun deleteDestination(destinationId: Int)
    }


