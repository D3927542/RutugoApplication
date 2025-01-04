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
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3) //Add the migration here
            .fallbackToDestructiveMigration()
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

    //Migration from version 1 to 2
    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS destinations_temp (
                id TEXT PRIMARY KEY NOT NULL,
                name TEXT NOT NULL,
                description TEXT NOT NULL,
                imageUrl TEXT,
                imageResId INTEGER,
                location TEXT

            )
            """.trimIndent()
            )

            //Copy data from the old table to the new table
            db.execSQL(
                """
                INSERT INTO destinations_temp(id, name, description, imageUrl, imageResId, location)
                SELECT id, name, description, imageUrl, imageResId, location
                FROM destinations
            """.trimIndent()
            )

            //drop the old table
            db.execSQL("DROP TABLE IF EXISTS destinations")

            //Rename the temporary table
            db.execSQL("ALTER TABLE destinations_temp RENAME TO destinations")
        }

    }

    // Migration from version 2 to 3
    private val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            val cursor = db.query("PRAGMA table_info(destinations)")
            var columnExists = false
            while (cursor.moveToNext()) {
                val columnName = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                if (columnName == "hotels") {
                    columnExists = true
                    break
                }
            }
            cursor.close()

            //Add 'hotels' column only if it does not exist
            if (!columnExists) {
                db.execSQL(
                    """
                ALTER TABLE destinations ADD COLUMN hotels TEXT
                """
                )
            }
        }
    }
}