package com.a160131_zwolak_dawid.components.Dashboard.BMI

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.a160131_zwolak_dawid.R
import com.a160131_zwolak_dawid.components.Layout.showToast
import com.a160131_zwolak_dawid.components.Navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun BmiScreen(
    auth: FirebaseAuth,
    navController: NavController,
    initialHeight: String,
    initialWeight: String
) {
    val context = LocalContext.current
    val uid = auth.currentUser?.uid ?: return

    var height by remember { mutableStateOf(initialHeight) }
    var weight by remember { mutableStateOf(initialWeight) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            OutlinedTextField(
                value = height,
                onValueChange = {
                    if (it.matches(Regex("^\\d*(\\.\\d*)?$"))) {
                        height = it
                    }
                },
                label = { Text(stringResource(R.string.height_cm)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = weight,
                onValueChange = {
                    if (it.matches(Regex("^\\d*(\\.\\d*)?$"))) {
                        weight = it
                    }
                },
                label = { Text(stringResource(R.string.weight_kg)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val h = height.toFloatOrNull()
                    val w = weight.toFloatOrNull()

                    if (h == null || w == null) {
                        showToast(context, context.getString(R.string.invalid_input))
                        return@Button
                    }

                    FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(uid)
                        .collection("profile")
                        .document("bmi")
                        .set(mapOf("height" to h, "weight" to w))
                        .addOnSuccessListener {
                            navController.previousBackStackEntry
                                ?.savedStateHandle
                                ?.set("height", h)

                            navController.previousBackStackEntry
                                ?.savedStateHandle
                                ?.set("weight", w)

                            if(initialHeight == "" && initialWeight == ""){
                                navController.navigate(Routes.DASHBOARD) {

                                    popUpTo(Routes.USER_BMI) { inclusive = true }
                                }
                            }else{
                                navController.popBackStack()
                            }


                        }
                        .addOnFailureListener {
                            showToast(
                                context,
                                context.getString(R.string.failed_save_bmi)
                            )
                        }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.save))
            }
        }
    }
}
