package com.example.mypinapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypinapp.data.AppDatabase
import com.example.mypinapp.data.Pin
import com.example.mypinapp.data.PinGroup
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PinViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val pinDao = db.pinDao()

    val allPins: StateFlow<List<Pin>> = pinDao.getAllPins()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allGroups: StateFlow<List<PinGroup>> = pinDao.getAllGroups()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addPin(latitude: Double, longitude: Double, title: String, description: String, groupId: Long? = null) {
        viewModelScope.launch {
            pinDao.insertPin(
                Pin(
                    latitude = latitude,
                    longitude = longitude,
                    title = title,
                    description = description,
                    groupId = groupId
                )
            )
        }
    }

    fun addGroup(name: String) {
        viewModelScope.launch {
            pinDao.insertGroup(PinGroup(name = name))
        }
    }

    fun deletePin(pin: Pin) {
        viewModelScope.launch {
            pinDao.deletePin(pin)
        }
    }
}