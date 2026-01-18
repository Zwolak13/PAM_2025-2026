package com.a160131_zwolak_dawid.data.firestore


import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

fun addExerciseToSet(
    uid: String,
    setId: String,
    exerciseId: String
): Task<Void> {
    return  FirebaseFirestore.getInstance()
        .collection("users")
        .document(uid)
        .collection("workoutSets")
        .document(setId)
        .collection("exercises")
        .document(exerciseId)
        .set(
            mapOf(
                "exerciseRefId" to exerciseId,
                "addedAt" to FieldValue.serverTimestamp()
            )
        )
}

fun createSetAndAddExercise(
    uid: String,
    setName: String,
    exerciseId: String
): Task<Void> {

    val db = FirebaseFirestore.getInstance()
    val setRef = db
        .collection("users")
        .document(uid)
        .collection("workoutSets")
        .document()

    return  setRef.set(
        mapOf(
            "name" to setName,
            "createdAt" to FieldValue.serverTimestamp()
        )
    ).addOnSuccessListener {
        setRef.collection("exercises")
            .document(exerciseId)
            .set(
                mapOf(
                    "exerciseRefId" to exerciseId,
                    "addedAt" to FieldValue.serverTimestamp()
                )
            )


    }
}
