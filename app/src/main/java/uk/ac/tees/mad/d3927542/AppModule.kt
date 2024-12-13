package uk.ac.tees.mad.d3927542

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uk.ac.tees.mad.d3927542.data.DestinationDao
import uk.ac.tees.mad.d3927542.data.FirebaseRepository
import uk.ac.tees.mad.d3927542.data.RoomRepository
import uk.ac.tees.mad.d3927542.data.UserDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(context, UserDatabase::class.java, "user_database")
            .addMigrations(MIGRATION_1_2) //Add the migration here
            .build()
    }

    @Provides
    @Singleton
    fun provideDestinationDao(userDatabase: UserDatabase): DestinationDao {
        return userDatabase.destinationDao()
    }

    @Provides
    @Singleton
    fun provideFirebaseRepository(firestore: FirebaseFirestore): FirebaseRepository {
        return FirebaseRepository(firestore)
    }

    @Provides
    @Singleton
    fun provideRoomRepository(destinationDao: DestinationDao): RoomRepository {
        return RoomRepository(destinationDao)
    }

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS destinations (
                id TEXT PRIMARY KEY NOT NULL,
                name TEXT NOT NULL,
                description TEXT NOT NULL,
                imageUrl TEXT NOT NULL

            )
            """.trimIndent())
        }

    }    
 }