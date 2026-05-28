package com.example.mypinapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mypinapp.ui.theme.MyPinAppTheme

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mypinapp.ui.screens.MainScreen
import com.example.mypinapp.ui.screens.MapScreen
import com.example.mypinapp.ui.screens.QuickPinScreen
import com.example.mypinapp.ui.screens.SettingsScreen
import com.example.mypinapp.ui.screens.PinManagerScreen
import com.example.mypinapp.ui.viewmodel.PinViewModel
import com.google.android.gms.location.LocationServices

import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {}
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {}
                else -> { /* No location access granted */ }
            }
        }

        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ))

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            MyPinAppTheme {
                val navController = rememberNavController()
                val viewModel: PinViewModel = viewModel()
                val pins by viewModel.allPins.collectAsState()

                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        MainScreen(
                            onNavigateToMap = { navController.navigate("map") },
                            onNavigateToSettings = { navController.navigate("settings") },
                            onNavigateToQuickPin = { navController.navigate("quick_pin") },
                            onNavigateToPinManager = { navController.navigate("pin_manager") }
                        )
                    }
                    composable("map") {
                        MapScreen(
                            pins = pins,
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable("settings") {
                        SettingsScreen(onBack = { navController.popBackStack() })
                    }
                    composable("pin_manager") {
                        PinManagerScreen(
                            onBack = { navController.popBackStack() },
                            viewModel = viewModel
                        )
                    }
                    composable("quick_pin") {
                        QuickPinScreen(
                            onBack = { navController.popBackStack() },
                            onPlacePin = { title, description ->
                                try {
                                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                                        if (location != null) {
                                            viewModel.addPin(
                                                location.latitude,
                                                location.longitude,
                                                title,
                                                description
                                            )
                                            navController.popBackStack()
                                        }
                                    }
                                } catch (e: SecurityException) {
                                    // Handle missing permission
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}