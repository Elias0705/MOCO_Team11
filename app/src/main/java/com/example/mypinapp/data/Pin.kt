package com.example.mypinapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pins")
data class Pin(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val latitude: Double,
    val longitude: Double,
    val title: String,
    val description: String,
    val imagePath: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val groupId: Long? = null
)
