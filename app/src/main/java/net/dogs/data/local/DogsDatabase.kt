package net.dogs.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Dog::class], version = 1)
abstract class DogsDatabase : RoomDatabase() {

    abstract fun dog(): DogDao

    companion object {
        private var database: DogsDatabase? = null

        fun getInstance(context: Context): DogsDatabase? {
            if (database == null) {
                synchronized(DogsDatabase::class) {
                    database = Room.databaseBuilder(
                        context.applicationContext,
                        DogsDatabase::class.java,
                        "dogs.db"
                    ).build()
                }
            }
            return database!!
        }

        fun destroyInstance() {
            database = null
        }
    }

}