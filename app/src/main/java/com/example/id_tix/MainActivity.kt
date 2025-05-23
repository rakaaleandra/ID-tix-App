package com.example.id_tix

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

data class FilmList(
    val title: String,
    val poster:Int,
)

val filmList = listOf(
    FilmList("Joker", R.drawable.ic_launcher_poster),
    FilmList("Star Wars", R.drawable.ic_launcher_poster2),
    FilmList("Blade Runner 2049", R.drawable.ic_launcher_poster3),
    FilmList("Spiderman Far From Home", R.drawable.ic_launcher_poster4),
    FilmList("Avengers Endgame", R.drawable.ic_launcher_poster5),
    FilmList("La La Land", R.drawable.ic_launcher_poster6),
    FilmList("The Grand Budapest Hotel", R.drawable.ic_launcher_poster7),
    FilmList("Minecraft Movie", R.drawable.ic_launcher_poster8),
    FilmList("Interstellar", R.drawable.ic_launcher_poster9),

)

@Composable
fun Header(navController: NavController){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(15.dp,35.dp,15.dp,15.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_logo_light),
            contentDescription = "Logo",
            modifier = Modifier
                .size(30.dp)
                .clickable{
                    navController.navigate("Home")
                }
        )
        Image(
            painter = painterResource(R.drawable.ic_launcher_profile),
            contentDescription = "Profile",
            modifier = Modifier
                .size(30.dp)
                .clickable{
                    navController.navigate("login")
                }
        )
    }
}

@Composable
fun Body(props: List<FilmList>){
    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),
        contentPadding = PaddingValues(15.dp,0.dp,15.dp,15.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp),
        horizontalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        item(span = {
            GridItemSpan(maxLineSpan)
        }) {
            Text(
                text = "Now Showing",
                style = TextStyle(
                    fontSize = 30.sp
                ),
//                modifier = Modifier.padding(15.dp),
                fontWeight = FontWeight.Bold,
            )
        }
        items(props) { prop ->
            Boxing(propo = prop)
        }
    }
}

@Composable
fun Boxing(propo : FilmList){
    Column {
        Image(
            painter = painterResource(id = propo.poster),
            contentDescription = "poster",
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(10.dp))
        Surface(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = propo.title,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HomePage(modifier: Modifier = Modifier){
    IDtixTheme {
        Surface(modifier = modifier.fillMaxSize()) {
            Column {
//                Text(
//                    text = "Now Showing",
//                    style = TextStyle(
//                        fontSize = 30.sp
//                    ),
//                    modifier = Modifier.padding(15.dp),
//                    fontWeight = FontWeight.Bold,
//                )
                Body(props =  filmList)
            }
        }
    }
}