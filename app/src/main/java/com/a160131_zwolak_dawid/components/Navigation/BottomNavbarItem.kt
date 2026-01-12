package com.a160131_zwolak_dawid.components.Navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavbarItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Search : BottomNavbarItem("search", Icons.Default.Search, "Szukaj")
    object Home : BottomNavbarItem("home", Icons.Default.Home, "Home")
    object Logout : BottomNavbarItem("logout", Icons.AutoMirrored.Default.Logout, "Wyloguj")
}
