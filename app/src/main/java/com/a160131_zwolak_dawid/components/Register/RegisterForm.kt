package com.a160131_zwolak_dawid.components.Register

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.a160131_zwolak_dawid.R
import com.a160131_zwolak_dawid.components.Layout.showToast
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RegisterForm(
    auth: FirebaseAuth,
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var acceptTerms by remember { mutableStateOf(false) }



    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.register_email_label)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.register_password_label)) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text(stringResource(R.string.register_confirm_password_label)) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(
                    value = acceptTerms,
                    onValueChange = { acceptTerms = it }
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = acceptTerms,
                onCheckedChange = null,
                modifier = Modifier.
                padding(top = 8.dp, bottom = 8.dp, end = 8.dp)
            )

            Text(stringResource(R.string.register_accept_terms))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (!email.contains("@") || !email.contains(".")) {
                    showToast(context,context.getString(R.string.error_invalid_email))
                    return@Button
                }

                if (password.length < 6) {
                    showToast(context,context.getString(R.string.error_invalid_password))
                    return@Button
                }

                if (password != confirmPassword) {
                    showToast(context,context.getString(R.string.error_passwords_not_match))
                    return@Button
                }

                if (!acceptTerms) {
                    showToast(context,context.getString(R.string.error_terms_not_accepted))
                    return@Button
                }

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            showToast(context,context.getString(R.string.register_success))
                            onRegisterSuccess()
                        } else {
                            showToast(context,task.exception?.message ?: context.getString(R.string.register_error_generic))
                        }
                    }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.register_button))
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onBackToLogin) {
            Text(stringResource(R.string.register_back_to_login))
        }
    }
}
