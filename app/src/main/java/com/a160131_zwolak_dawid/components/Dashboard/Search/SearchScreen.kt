package com.a160131_zwolak_dawid.components.Dashboard


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.a160131_zwolak_dawid.R
import com.a160131_zwolak_dawid.components.Dashboard.Search.ExerciseCard
import com.a160131_zwolak_dawid.model.Exercise
import com.a160131_zwolak_dawid.model.toExercise
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun SearchScreen() {
    val context = LocalContext.current
    var exercises by remember { mutableStateOf(listOf<Exercise>()) }
    var isLoading by remember { mutableStateOf(true) }
    val configuration = LocalConfiguration.current
    val userLang = configuration.locales[0].language

    LaunchedEffect(Unit) {
        FirebaseFirestore.getInstance()
            .collection("exercises")
            .get()
            .addOnSuccessListener { snapshot ->
                exercises = snapshot.documents.map { it.toExercise() }
                isLoading = false
            }
            .addOnFailureListener {
                isLoading = false
            }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (exercises.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = context.getString(R.string.no_exercises))
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(exercises) { exercise ->
                ExerciseCard(exercise = exercise, userLang = userLang)
            }
        }
    }
}