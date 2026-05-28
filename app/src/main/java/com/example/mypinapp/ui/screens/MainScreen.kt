package com.example.mypinapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

import androidx.compose.ui.tooling.preview.Preview
import com.example.mypinapp.ui.theme.MyPinAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToMap: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToQuickPin: () -> Unit,
    onNavigateToPinManager: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MyPin App") },
                navigationIcon = {
                    IconButton(onClick = { /* Open Drawer */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item {
                MenuItem(
                    icon = Icons.Default.Map,
                    title = "Pin Map",
                    badge = "24",
                    onClick = onNavigateToMap
                )
            }
            item {
                MenuItem(
                    icon = Icons.Default.Settings,
                    title = "App Settings",
                    onClick = onNavigateToSettings
                )
            }
            item {
                MenuItem(
                    icon = Icons.Default.AddLocation,
                    title = "Quick Pin",
                    onClick = onNavigateToQuickPin
                )
            }
            item {
                MenuItem(
                    icon = Icons.Default.List,
                    title = "Pin Manager",
                    onClick = onNavigateToPinManager
                )
            }
            
            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    text = "Labels",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            
            // Placeholder labels
            items(listOf("Label 01", "Label 02", "Label 03")) { label ->
                ListItem(
                    headlineContent = { Text(label) },
                    leadingContent = { Icon(Icons.Default.Folder, contentDescription = null) },
                    modifier = Modifier.clickable { /* Filter by label */ }
                )
            }
        }
    }
}

@Composable
fun MenuItem(
    icon: ImageVector,
    title: String,
    badge: String? = null,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = { Text(title) },
        leadingContent = { Icon(icon, contentDescription = null) },
        trailingContent = badge?.let { { Text(it) } },
        modifier = Modifier.clickable { onClick() }
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MyPinAppTheme {
        MainScreen(
            onNavigateToMap = {},
            onNavigateToSettings = {},
            onNavigateToQuickPin = {},
            onNavigateToPinManager = {}
        )
    }
}