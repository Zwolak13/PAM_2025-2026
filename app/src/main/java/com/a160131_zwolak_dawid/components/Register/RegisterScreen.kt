package com.a160131_zwolak_dawid.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.a160131_zwolak_dawid.components.Layout.Logo
import com.a160131_zwolak_dawid.components.Register.RegisterForm
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RegisterScreen(
    auth: FirebaseAuth,
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Logo()

            Spacer(modifier = Modifier.height(32.dp))

            RegisterForm(
                auth = auth,
                onRegisterSuccess = onRegisterSuccess,
                onBackToLogin = onBackToLogin
            )
        }
    }
}
