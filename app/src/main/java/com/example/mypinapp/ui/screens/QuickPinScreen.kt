package com.example.mypinapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.mypinapp.ui.theme.MyPinAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickPinScreen(
    onBack: () -> Unit,
    onPlacePin: (String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quick Pin") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { /* Back to Main Menu */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Back to Mainmenu")
            }

            Spacer(Modifier.height(16.dp))

            Text("Quick Pin Advanced Settings", style = MaterialTheme.typography.bodySmall)
            
            Spacer(Modifier.height(16.dp))
            
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                // Image placeholder from wireframe
                Text("Image")
            }
            
            Spacer(Modifier.height(16.dp))
            
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(Modifier.weight(1f))
            
            LargeFloatingActionButton(
                onClick = { onPlacePin(title, description) },
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(32.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Place Quick Pin", style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuickPinScreenPreview() {
    MyPinAppTheme {
        QuickPinScreen(onBack = {}, onPlacePin = { _, _ -> })
    }
}
