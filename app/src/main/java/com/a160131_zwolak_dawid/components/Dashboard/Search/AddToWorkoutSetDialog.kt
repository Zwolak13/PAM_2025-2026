package com.a160131_zwolak_dawid.components.Dashboard.Search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.a160131_zwolak_dawid.R
import com.a160131_zwolak_dawid.components.Layout.showToast
import com.a160131_zwolak_dawid.data.firestore.addExerciseToSet
import com.a160131_zwolak_dawid.data.firestore.createSetAndAddExercise
import com.a160131_zwolak_dawid.model.WorkoutSet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun AddToWorkoutSetDialog(
    exerciseId: String,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

    var sets by remember { mutableStateOf<List<WorkoutSet>>(emptyList()) }
    var newSetName by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(uid)
            .collection("workoutSets")
            .get()
            .addOnSuccessListener { snapshot ->
                sets = snapshot.documents.map {
                    WorkoutSet(
                        id = it.id,
                        name = it.getString("name") ?: ""
                    )
                }
                isLoading = false
            }
            .addOnFailureListener {
                isLoading = false
                showToast(context, context.getString(R.string.error_fetching_sets))
            }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        title = { Text(context.getString(R.string.add_to_workout_set_title)) },
        text = {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Column {
                    if (sets.isNotEmpty()) {
                        sets.forEach { set ->
                            Text(
                                text = set.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        addExerciseToSet(uid, set.id, exerciseId)
                                            .addOnSuccessListener {
                                                showToast(context, context.getString(R.string.exercise_added))
                                                onDismiss()
                                            }
                                            .addOnFailureListener {
                                                showToast(context, context.getString(R.string.failed_add_exercise))
                                            }
                                    }
                                    .padding(12.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    } else {
                        Text(
                            text = context.getString(R.string.no_workout_sets),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    OutlinedTextField(
                        value = newSetName,
                        onValueChange = { newSetName = it },
                        label = { Text(context.getString(R.string.new_workout_set_name)) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            createSetAndAddExercise(uid, newSetName, exerciseId)
                                .addOnSuccessListener {
                                    showToast(context, context.getString(R.string.created_and_added))
                                    onDismiss()
                                }
                                .addOnFailureListener {
                                    showToast(context, context.getString(R.string.failed_create_set))
                                }
                        },
                        enabled = newSetName.isNotBlank(),
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(context.getString(R.string.create_and_add))
                    }
                }
            }
        }
    )
}
