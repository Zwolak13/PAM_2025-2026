package com.a160131_zwolak_dawid.components.Dashboard.MainScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun DashboardScreen(
    navController: NavController,
    auth: FirebaseAuth,
    initialHeight: Float = 0f,
    initialWeight: Float = 0f
) {
    var height by remember { mutableStateOf(initialHeight) }
    var weight by remember { mutableStateOf(initialWeight) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(auth.currentUser) {
        val user = auth.currentUser
        if(user != null){
            try {
                val doc = FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(user.uid)
                    .collection("profile")
                    .document("bmi")
                    .get()
                    .await()
                if(doc.exists()){
                    // Firebase przechowuje Double, rzutujemy na Float
                    height = doc.getDouble("height")?.toFloat() ?: 0f
                    weight = doc.getDouble("weight")?.toFloat() ?: 0f
                }
            } catch (_: Exception){}
        }
        isLoading = false
    }

    if(isLoading){
        androidx.compose.foundation.layout.Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ){
            androidx.compose.material3.CircularProgressIndicator()
        }
    } else {
        DashboardContent(
            auth = auth,
            height = height,
            weight = weight,
            navController = navController
        )
    }
}
