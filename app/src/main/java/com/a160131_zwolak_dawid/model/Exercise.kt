package com.a160131_zwolak_dawid.model

import com.google.firebase.firestore.DocumentSnapshot

data class Exercise(
    val id: String = "",
    val name: Map<String, String> = mapOf(),
    val description: Map<String, String> = mapOf(),
    val equipment: Map<String, String> = mapOf(),
    val musclesGroup: Map<String, List<String>> = mapOf(),
    val difficulty: Int = 0
)

fun DocumentSnapshot.toExercise(): Exercise {
    return Exercise(
        id = id,
        name = get("name") as? Map<String, String> ?: mapOf(),
        description = get("description") as? Map<String, String> ?: mapOf(),
        equipment = get("equipment") as? Map<String, String> ?: mapOf(),
        musclesGroup = get("musclesGroup") as? Map<String, List<String>> ?: mapOf(),
        difficulty = (getLong("difficulty") ?: 0).toInt()
    )
}

