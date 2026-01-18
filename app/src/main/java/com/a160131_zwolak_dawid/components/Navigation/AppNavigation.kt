package com.a160131_zwolak_dawid.components.Navigation

import BottomNavBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.a160131_zwolak_dawid.components.Dashboard.BMI.BmiScreen
import com.a160131_zwolak_dawid.components.Dashboard.MainScreen.DashboardScreen
import com.a160131_zwolak_dawid.components.Dashboard.SearchScreen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.a160131_zwolak_dawid.components.Login.LoginScreen
import com.a160131_zwolak_dawid.screens.RegisterScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun AppNavigation(auth: FirebaseAuth) {
    val navController = rememberNavController()

    val bottomNavRoutes = listOf(
        "home",
        "search"
    )

    var height by remember { mutableStateOf(0f) }
    var weight by remember { mutableStateOf(0f) }
    var isLoadingUser by remember { mutableStateOf(true) }
    var startDestination by remember { mutableStateOf(Routes.LOGIN) }

    LaunchedEffect(auth.currentUser) {
        val user = auth.currentUser
        startDestination = if (user != null) {
            try {
                val doc = FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(user.uid)
                    .collection("profile")
                    .document("bmi")
                    .get()
                    .await()
                if (doc.exists()) {
                    height = doc.getDouble("height")?.toFloat() ?: 0f
                    weight = doc.getDouble("weight")?.toFloat() ?: 0f
                    Routes.DASHBOARD
                } else {
                    Routes.USER_BMI
                }
            } catch (e: Exception) {
                Routes.USER_BMI
            }
        } else {
            Routes.LOGIN
        }
        isLoadingUser = false
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in bottomNavRoutes

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController)
            }
        }
    ) { padding ->
        if (isLoadingUser) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(padding)
            ) {
                composable(Routes.LOGIN) {
                    LoginScreen(
                        auth = auth,
                        onLoginSuccess = {
                            navController.navigate(Routes.DASHBOARD) {
                                popUpTo(Routes.LOGIN) { inclusive = true }
                            }
                        },
                        onRegisterClick = { navController.navigate(Routes.REGISTER) }
                    )
                }

                composable(Routes.REGISTER) {
                    RegisterScreen(
                        auth = auth,
                        onRegisterSuccess = { navController.popBackStack() },
                        onBackToLogin = { navController.popBackStack() }
                    )
                }

                composable(Routes.DASHBOARD) {
                    DashboardScreen(auth = auth, initialHeight = height, initialWeight = weight, navController = navController)
                }

                composable("home") {
                    DashboardScreen(auth = auth, initialHeight = height, initialWeight = weight, navController = navController)
                }

                composable("search") {
                    SearchScreen()
                }

                composable("logout") {
                    auth.signOut()
                    navController.navigate(Routes.LOGIN) {
                        popUpTo("home") { inclusive = true }
                    }
                }

                composable(
                    route = Routes.USER_BMI,
                    arguments = listOf(
                        navArgument("height") {
                            type = NavType.StringType
                            defaultValue = ""
                        },
                        navArgument("weight") {
                            type = NavType.StringType
                            defaultValue = ""
                        }
                    )
                ) { backStackEntry ->
                    val height = backStackEntry.arguments?.getString("height") ?: ""
                    val weight = backStackEntry.arguments?.getString("weight") ?: ""

                    BmiScreen(
                        auth = auth,
                        navController = navController,
                        initialHeight = height,
                        initialWeight = weight
                    )
                }


            }
        }
    }
}
