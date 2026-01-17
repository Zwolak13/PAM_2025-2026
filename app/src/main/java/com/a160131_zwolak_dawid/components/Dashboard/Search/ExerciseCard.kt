package com.a160131_zwolak_dawid.components.Dashboard.Search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a160131_zwolak_dawid.R
import com.a160131_zwolak_dawid.model.Exercise
import kotlin.collections.joinToString

@Composable
fun ExerciseCard(exercise: Exercise, userLang: String) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier
            .padding(16.dp)
        ) {
            Text(
                text = exercise.name[userLang] ?: exercise.name["en"] ?: "Unknown",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,

                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = exercise.description[userLang] ?: exercise.description["en"] ?: "",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            val muscles = exercise.muscleGroup[userLang] ?: exercise.muscleGroup["en"] ?: emptyList()
            Text(
                text = "${context.getString(R.string.muscle_label)} ${muscles.joinToString(", ")} | ${context.getString(R.string.difficulty_label)} ${exercise.difficulty}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        }
    }
}