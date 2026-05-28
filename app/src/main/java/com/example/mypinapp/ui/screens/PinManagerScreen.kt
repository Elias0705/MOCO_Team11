package com.example.mypinapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mypinapp.ui.theme.MyPinAppTheme
import com.example.mypinapp.ui.viewmodel.PinViewModel
import com.example.mypinapp.data.Pin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PinManagerScreen(
    onBack: () -> Unit,
    viewModel: PinViewModel = viewModel()
) {
    val pins by viewModel.allPins.collectAsState()
    var pinToDelete by remember { mutableStateOf<Pin?>(null) }

    if (pinToDelete != null) {
        AlertDialog(
            onDismissRequest = { pinToDelete = null },
            title = { Text("Pin löschen") },
            text = { Text("Möchtest du den Pin '${pinToDelete?.title}' wirklich löschen?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        pinToDelete?.let { viewModel.deletePin(it) }
                        pinToDelete = null
                    }
                ) {
                    Text("Löschen", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { pinToDelete = null }) {
                    Text("Abbrechen")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pin Manager") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text("Deine Pins", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))
            }

            if (pins.isEmpty()) {
                item {
                    Text("Keine Pins vorhanden.", style = MaterialTheme.typography.bodyMedium)
                }
            }

            items(pins) { pin ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = pin.title, style = MaterialTheme.typography.titleMedium)
                            if (pin.description.isNotEmpty()) {
                                Text(text = pin.description, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                        IconButton(onClick = { pinToDelete = pin }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Löschen",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PinManagerScreenPreview() {
    MyPinAppTheme {
        PinManagerScreen(onBack = {})
    }
}
