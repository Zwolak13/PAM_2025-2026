package com.a160131_zwolak_dawid.components.Dashboard.MainScreen


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.a160131_zwolak_dawid.R
import com.a160131_zwolak_dawid.components.Layout.showToast
import com.a160131_zwolak_dawid.model.WorkoutSet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun DashboardContent(
    auth: FirebaseAuth,
    height: Float,
    weight: Float,
    navController: NavController) {
    val context = LocalContext.current
    val uid = auth.currentUser?.uid ?: return
    val lang = context.resources.configuration.locales[0].language


    var sets by remember { mutableStateOf<List<WorkoutSet>>(emptyList()) }
    var expandedSets by remember { mutableStateOf<Map<String, List<String>>>(emptyMap()) }
    var loading by remember { mutableStateOf<Map<String, Boolean>>(emptyMap()) }
    var isLoadingSets by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(uid)
            .collection("workoutSets")
            .get()
            .addOnSuccessListener { snap ->
                sets = snap.documents.map {
                    WorkoutSet(
                        id = it.id,
                        name = it.getString("name") ?: ""
                    )
                }
                isLoadingSets = false
            }
            .addOnFailureListener {
                isLoadingSets = false
                showToast(context, context.getString(R.string.failed_fetch_sets))
            }
    }


    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        BmiCard(height = height, weight= weight ,navController = navController )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.dashboard_title),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (isLoadingSets) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn {
                items(sets) { set ->
                    WorkoutSetBox(
                        name = set.name,
                        exercises = expandedSets[set.id],
                        isLoading = loading[set.id] == true,
                        isExpanded = expandedSets.containsKey(set.id),
                        onToggle = {
                            if (expandedSets.containsKey(set.id)) {
                                expandedSets = expandedSets - set.id
                            } else {
                                loading = loading + (set.id to true)

                                FirebaseFirestore.getInstance()
                                    .collection("users")
                                    .document(uid)
                                    .collection("workoutSets")
                                    .document(set.id)
                                    .collection("exercises")
                                    .get()
                                    .addOnSuccessListener { snap ->
                                        val ids = snap.documents.mapNotNull {
                                            it.getString("exerciseRefId")
                                        }

                                        if (ids.isEmpty()) {
                                            expandedSets = expandedSets + (set.id to emptyList())
                                            loading = loading + (set.id to false)
                                            return@addOnSuccessListener
                                        }

                                        FirebaseFirestore.getInstance()
                                            .collection("exercises")
                                            .whereIn(FieldPath.documentId(), ids.take(10))
                                            .get()
                                            .addOnSuccessListener { exSnap ->
                                                val names = exSnap.documents.map {
                                                    it.getString("name.$lang") ?: "Unknown"
                                                }
                                                expandedSets = expandedSets + (set.id to names)
                                                loading = loading + (set.id to false)
                                            }
                                            .addOnFailureListener {
                                                loading = loading + (set.id to false)
                                                showToast(
                                                    context,
                                                    context.getString(R.string.failed_fetch_exercises)
                                                )
                                            }
                                    }
                                    .addOnFailureListener {
                                        loading = loading + (set.id to false)
                                        showToast(
                                            context,
                                            context.getString(R.string.failed_fetch_exercises)
                                        )
                                    }
                            }
                        },
                    )
                }
            }
        }
    }
}
