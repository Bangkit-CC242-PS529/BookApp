package com.example.bookapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.bookapp.ui.navigation.BottomNavigationBar
import com.example.bookapp.ui.navigation.Routes

@Composable
fun SoonScreen(
    isConnected: Boolean,
    onNavigateToWord: () -> Unit,
    onNavigateToReadingList: () -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                onWordClick = {
                    if (isConnected) onNavigateToWord() else {}
                },
                onReadingListClick = {
                    if (isConnected) onNavigateToReadingList() else {}
                },
                onSoonClick = { /* Current */ },
                currentRoute = Routes.Soon.route
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Under Developed")
        }
    }
}
