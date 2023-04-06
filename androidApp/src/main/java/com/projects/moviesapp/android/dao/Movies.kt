package com.projects.moviesapp.android.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "my_database")
data class Movies(
    @PrimaryKey(autoGenerate = true)
    @NotNull
    val id:Int,
    val name:String,
)
