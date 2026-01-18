import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.a160131_zwolak_dawid.R

sealed class BottomNavbarItem(
    val route: String,
    val icon: ImageVector,
    val label: Int
) {
    object Search : BottomNavbarItem("search", Icons.Default.Search, R.string.nav_search)
    object Home : BottomNavbarItem("home", Icons.Default.Home, R.string.nav_home)
    object Logout : BottomNavbarItem("logout", Icons.AutoMirrored.Default.Logout, R.string.nav_logout)
}
