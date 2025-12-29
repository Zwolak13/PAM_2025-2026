package com.a160131_zwolak_dawid.components.Login

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.a160131_zwolak_dawid.components.Layout.Logo
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(
    auth: FirebaseAuth,
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Logo()

        Spacer(modifier = Modifier.height(24.dp))

        LoginForm(
            auth = auth,
            onLoginSuccess = onLoginSuccess,
            onRegisterClick = onRegisterClick
        )
    }
}

