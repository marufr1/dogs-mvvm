package com.desinta.dogsmvvmtrial.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "dogs")
@Parcelize
data class Dog(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "image_id") var imageId: String,
    @ColumnInfo(name = "url") var url: String,
    @ColumnInfo var name: String,
    @ColumnInfo(name = "bred_for") var bredFor: String
) : Parcelable