package com.a160131_zwolak_dawid.components.Navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.a160131_zwolak_dawid.components.Dashboard.DashboardScreen
import com.a160131_zwolak_dawid.components.Dashboard.SearchScreen


import com.a160131_zwolak_dawid.components.Login.LoginScreen
import com.a160131_zwolak_dawid.screens.RegisterScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation(auth: FirebaseAuth) {
    val navController = rememberNavController()

    val bottomNavRoutes = listOf(
        BottomNavbarItem.Search.route,
        BottomNavbarItem.Home.route,
        BottomNavbarItem.Logout.route
    )

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    val showBottomBar = currentRoute in bottomNavRoutes

    val startDestination = if (auth.currentUser != null) {
        BottomNavbarItem.Home.route
    } else {
        Routes.LOGIN
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController)
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(padding)
        ) {
            composable(Routes.LOGIN) {
                LoginScreen(
                    auth = auth,
                    onLoginSuccess = {
                        navController.navigate(BottomNavbarItem.Home.route) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    },
                    onRegisterClick = {
                        navController.navigate(Routes.REGISTER)
                    }
                )
            }

            composable(Routes.REGISTER) {
                RegisterScreen(
                    auth = auth,
                    onRegisterSuccess = {
                        navController.popBackStack()
                    },
                    onBackToLogin = {
                        navController.popBackStack()
                    }
                )
            }

            composable(BottomNavbarItem.Home.route) {
                DashboardScreen(auth = auth, onLogout = {
                    auth.signOut()
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }

                })
            }

            composable(BottomNavbarItem.Search.route) {
                SearchScreen()
            }

            composable(BottomNavbarItem.Logout.route) {
                auth.signOut()
                navController.navigate(Routes.LOGIN) {
                    popUpTo(BottomNavbarItem.Home.route) { inclusive = true }
                }
            }
        }
    }
}

