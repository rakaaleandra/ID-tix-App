package com.example.id_tix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Upcoming
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.id_tix.ui.theme.*
import androidx.compose.ui.text.font.FontWeight
import com.example.id_tix.filmList
import com.example.id_tix.comingSoonList
import com.example.id_tix.FilmList
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.id_tix.ui.theme.IDtixTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authViewModel: AuthViewModel by viewModels()
        setContent {
            IDtixTheme {
//                 MainScreen()
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {Header(navController)}){ innerPadding ->
//                ){ innerPadding ->

                    MyAppNavigation(modifier = Modifier.padding(innerPadding), authViewModel = authViewModel, navHostController = navController)
//                    aslfgnawusiogbnbno
//                    Text(text = "KONTOL", modifier = Modifier.padding(innerPadding))
//                    HomePage(modifier = Modifier.padding(innerPadding))
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
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            if (currentRoute?.startsWith("film_detail") == true) {
                DetailHeader(navController)
            } else {
                Header()
            }
        },
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            if (currentRoute?.startsWith("film_detail") != true) {
                BottomNavigationBar(navController)
            }
        },
        containerColor = BackgroundGray
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "now_showing",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("now_showing") {
                FilmScreen("Now Showing", filmList, navController)
            }
            composable("coming_soon") {
                FilmScreen("Coming Soon", comingSoonList, navController)
            }
            composable("film_detail/{filmId}") { backStackEntry ->
                val filmId = backStackEntry.arguments?.getString("filmId")?.toIntOrNull()
                val allFilms = filmList + comingSoonList
                val film = allFilms.find { it.id == filmId }
                film?.let { FilmDetailScreen(it) }
            }
        }
    }
}

@Composable
fun Header(navController: NavController) {
    Surface(color = PrimaryDark, shadowElevation = 4.dp) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .statusBarsPadding()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(32.dp)
                      .clickable{
                        navController.navigate("Home")
                    }
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

// fun Header(navController: NavController){
//     Row(
//         verticalAlignment = Alignment.CenterVertically,
//         horizontalArrangement = Arrangement.SpaceBetween,
//         modifier = Modifier.fillMaxWidth().padding(15.dp,35.dp,15.dp,15.dp)
//     ) {
//         Image(
//             painter = painterResource(R.drawable.ic_launcher_logo_light),
//             contentDescription = "Logo",
//             modifier = Modifier
//                 .size(30.dp)
//                 .clickable{
//                     navController.navigate("Home")
//                 }
//         )
//         Image(
//             painter = painterResource(R.drawable.ic_launcher_profile),
//             contentDescription = "Profile",
//             modifier = Modifier
//                 .size(30.dp)
//                 .clickable{
//                     navController.navigate("login")
//                 }
//         )
    }
}

@Composable
fun FilmScreen(title: String, films: List<FilmList>, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        Text(
            text = title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryDark,
            modifier = Modifier.padding(start = 20.dp, top = 24.dp, bottom = 16.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(films) { film ->
                FilmCard(film) {
                    navController.navigate("film_detail/${film.id}")
                }
            }
// fun Body(props: List<FilmList>){
//     LazyVerticalGrid(
//         columns = GridCells.Adaptive(128.dp),
//         contentPadding = PaddingValues(15.dp,0.dp,15.dp,15.dp),
//         verticalArrangement = Arrangement.spacedBy(30.dp),
//         horizontalArrangement = Arrangement.spacedBy(30.dp)
//     ) {
//         item(span = {
//             GridItemSpan(maxLineSpan)
//         }) {
//             Text(
//                 text = "Now Showing",
//                 style = TextStyle(
//                     fontSize = 30.sp
//                 ),
// //                modifier = Modifier.padding(15.dp),
//                 fontWeight = FontWeight.Bold,
//             )
//         }
//         items(props) { prop ->
//             Boxing(propo = prop)
        }
    }
}

@Composable
fun FilmCard(film: FilmList, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column {
            Image(
                painter = painterResource(id = film.poster),
                contentDescription = "Movie poster for ${film.title}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = film.title,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            }
// fun Boxing(propo : FilmList){
//     Column {
//         Image(
//             painter = painterResource(id = propo.poster),
//             contentDescription = "poster",
//             modifier = Modifier
//         )
//         Spacer(modifier = Modifier.height(10.dp))
//         Surface(modifier = Modifier.fillMaxWidth()) {
//             Text(
//                 text = propo.title,
//                 textAlign = TextAlign.Center,
//                 fontWeight = FontWeight.SemiBold
//             )
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
// fun HomePage(modifier: Modifier = Modifier){
//     IDtixTheme {
//         Surface(modifier = modifier.fillMaxSize()) {
//             Column {
// //                Text(
// //                    text = "Now Showing",
// //                    style = TextStyle(
// //                        fontSize = 30.sp
// //                    ),
// //                    modifier = Modifier.padding(15.dp),
// //                    fontWeight = FontWeight.Bold,
// //                )
//                 Body(props =  filmList)
            }
        }
    }
}