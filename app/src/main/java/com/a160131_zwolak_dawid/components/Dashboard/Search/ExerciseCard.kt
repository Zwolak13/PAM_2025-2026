package com.a160131_zwolak_dawid.components.Dashboard.Search

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a160131_zwolak_dawid.R
import com.a160131_zwolak_dawid.model.Exercise

@Composable
fun ExerciseCard(
    exercise: Exercise,
    userLang: String
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = exercise.name[userLang] ?: exercise.name["en"] ?: "Unknown",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                IconButton(onClick = { showDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add exercise",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = exercise.description[userLang] ?: exercise.description["en"] ?: "",
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            val muscles =
                exercise.musclesGroup[userLang] ?: exercise.musclesGroup["en"] ?: emptyList()

            Text(
                text = "${context.getString(R.string.muscle_label)} ${muscles.joinToString(", ")} | ${context.getString(R.string.difficulty_label)} ${exercise.difficulty}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        }
    }

    if (showDialog) {
        AddToWorkoutSetDialog(
            exerciseId = exercise.id,
            onDismiss = { showDialog = false }
        )
    }
}
