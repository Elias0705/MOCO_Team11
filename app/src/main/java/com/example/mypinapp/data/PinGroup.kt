package com.example.mypinapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pin_groups")
data class PinGroup(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)
