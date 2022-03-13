package com.desinta.dogsmvvmtrial.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DogDao {

    @Query("select * from dogs")
    fun getDog(): List<Dog>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDog(dogs: List<Dog>)

}