package com.a160131_zwolak_dawid.components.Dashboard.MainScreen

import androidx.compose.runtime.*
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.NavController


@Composable
fun DashboardScreen(
    navController: NavController,
    auth: FirebaseAuth,
    initialHeight: Float,
    initialWeight: Float
) {
    var height by remember { mutableStateOf(initialHeight) }
    var weight by remember { mutableStateOf(initialWeight) }

    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle

    LaunchedEffect(Unit) {
        savedStateHandle?.getLiveData<Float>("height")?.observeForever {
            height = it
        }
        savedStateHandle?.getLiveData<Float>("weight")?.observeForever {
            weight = it
        }
    }

    DashboardContent(
        auth = auth,
        height = height,
        weight = weight,
        navController = navController
    )
}


