package com.example.id_tix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Upcoming
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.id_tix.ui.theme.*
import androidx.compose.ui.text.font.FontWeight
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.id_tix.pages.DetailHeader
import com.example.id_tix.pages.RegistrationHeader
import com.example.id_tix.pages.ScheduleHeader
import com.example.id_tix.pages.SeatSelectionHeader
import com.example.id_tix.pages.TicketHeader
import com.example.id_tix.ui.theme.IDtixTheme
import com.example.id_tix.pages.PaymentHeader
import com.example.id_tix.pages.HistoryHeader
import com.example.id_tix.pages.ProfileHeader
import com.example.id_tix.pages.TopUpHeader
import com.example.id_tix.pages.TopUpHeaderToHome

class MainActivity : ComponentActivity() {
    val authViewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IDtixTheme {
                val navController = rememberNavController()
                var isSplashScreenVisible by remember { mutableStateOf(true) }
                if (isSplashScreenVisible) {
                    SplashScreen(onAnimationEnd = {
                        isSplashScreenVisible = false
                    })
                } else {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    Scaffold(modifier = Modifier.fillMaxSize(),
                        topBar = {
                            when {
                                currentRoute?.startsWith("film_detail") == true -> {
                                    DetailHeader(navController)
                                }
                                currentRoute?.startsWith("schedule") == true -> {
                                    ScheduleHeader(navController)
                                }
                                currentRoute?.startsWith("seat_selection") == true -> {
                                    SeatSelectionHeader(navController)
                                }
                                currentRoute?.startsWith("payment") == true -> {
                                    PaymentHeader(navController)
                                }
                                currentRoute?.startsWith("ticket") == true -> {
                                    TicketHeader(navController)
                                }
                                currentRoute?.startsWith("login") == true ||
                                        currentRoute?.startsWith("signup") == true -> {
                                    TopUpHeaderToHome(navController)
                                }
                                currentRoute?.startsWith("history") == true -> {
                                    HistoryHeader(navController)
                                }
                                currentRoute?.startsWith("profile") == true -> {
                                    ProfileHeader(navController)
                                }
                                currentRoute?.startsWith("topup") == true -> {
                                    TopUpHeader(navController)
                                }
                                currentRoute?.startsWith("sukses") == true -> {
                                    BlankHeader()
                                }
                                else -> {
                                    Header(navController)
                                }
                            }
                        },
                        bottomBar = {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentRoute = navBackStackEntry?.destination?.route
                            val hideBottomBar = currentRoute?.startsWith("film_detail") == true ||
                                    currentRoute?.startsWith("login") == true ||
                                    currentRoute?.startsWith("signup") == true ||
                                    currentRoute?.startsWith("history") == true ||
                                    currentRoute?.startsWith("profile") == true ||
                                    currentRoute?.startsWith("topup") == true
                            val blankBottom =
                                    currentRoute?.startsWith("schedule") == true ||
                                    currentRoute?.startsWith("seat_selection") == true ||
                                    currentRoute?.startsWith("payment") == true ||
                                    currentRoute?.startsWith("ticket") == true || currentRoute?.startsWith("sukses") == true

                            if (blankBottom){
                                BlankBottom()
                            } else if(!hideBottomBar){
                                BottomNavigationBar(navController)
                            }
//                            if (!hideBottomBar) {
//                                BottomNavigationBar(navController)
//                            }
                        },
                        containerColor = BackgroundGray
                    ){ innerPadding ->
                        MyAppNavigation(modifier = Modifier.padding(innerPadding), authViewModel = authViewModel, navHostController = navController)
                    }
                }
            }
        }
    }
}

data class NavItem(
    val label: String,
    val route: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
fun Header(navController: NavController) {
    Surface(color = PrimaryDark, shadowElevation = 4.dp) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 6.dp)
                .statusBarsPadding()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "ID-Tix",
                    color = White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            IconButton(onClick = {
//                navController.navigate("profile")
                navController.navigate("login")
            }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    tint = White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Surface(tonalElevation = 8.dp) {
        NavigationBar(
            containerColor = White,
            contentColor = PrimaryDark
        ) {
            val items = listOf(
                NavItem("Now Showing", "now_showing", Icons.Default.Movie),
                NavItem("Coming Soon", "coming_soon", Icons.Default.Upcoming)
            )
            items.forEach { item ->
                NavigationBarItem(
                    selected = currentDestination?.route == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            item.icon,
                            contentDescription = item.label,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(text = item.label, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryDark,
                        selectedTextColor = PrimaryDark,
                        indicatorColor = SelectedIndicator,
                        unselectedIconColor = UnselectedGray,
                        unselectedTextColor = UnselectedGray
                    )
                )
            }
        }
    }
}

@Composable
fun BlankHeader(){
    Surface(color = PrimaryDark) {

    }
}

@Composable
fun BlankBottom(){
    Surface(color = PrimaryDark) {

    }
}