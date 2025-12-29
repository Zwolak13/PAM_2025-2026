package com.a160131_zwolak_dawid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.a160131_zwolak_dawid.components.Navigation.AppNavigation
import com.a160131_zwolak_dawid.ui.theme._160131_zwolak_DawidTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        setContent {
            _160131_zwolak_DawidTheme {
                AppNavigation(auth)
            }
        }
    }
}

