package com.a160131_zwolak_dawid.components.Login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.a160131_zwolak_dawid.R
import com.a160131_zwolak_dawid.components.Layout.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

@Composable
fun LoginForm(
    auth: FirebaseAuth,
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {
    var (email, setEmail) = remember { mutableStateOf("") }
    var (password, setPassword) = remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = setEmail,
            label = { Text(stringResource(R.string.email)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = setPassword,
            label = { Text(stringResource(R.string.password)) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onLoginSuccess()
                        } else {
                            val message = when (val e = task.exception) {
                                is FirebaseAuthException -> {
                                    when (e.errorCode) {
                                        "ERROR_INVALID_EMAIL" -> context.getString(R.string.error_invalid_email)
                                        "ERROR_WRONG_PASSWORD" -> context.getString(R.string.error_wrong_password)
                                        "ERROR_USER_NOT_FOUND" -> context.getString(R.string.error_user_not_found)
                                        "ERROR_TOO_MANY_REQUESTS" -> context.getString(R.string.error_too_many_requests)
                                        "ERROR_NETWORK_REQUEST_FAILED" -> context.getString(R.string.error_network)
                                        else -> context.getString(R.string.error_generic)
                                    }
                                }
                                else -> context.getString(R.string.error_generic)
                            }

                            showToast(context, message)
                        }

                    }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.login))
        }

        Spacer(modifier = Modifier.height(6.dp))

        TextButton(
            onClick = onRegisterClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.login_register_button))
        }


    }
}
