import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        BottomNavbarItem.Search,
        BottomNavbarItem.Home,
        BottomNavbarItem.Logout
    )

    val navBackStackEntryState = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntryState.value?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                    }
                },
                icon = { Icon(item.icon, contentDescription = stringResource(item.label)) },
                label = { Text(stringResource(item.label)) }
            )
        }
    }
}
