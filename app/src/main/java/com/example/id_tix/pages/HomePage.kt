package com.example.id_tix.pages

import com.example.id_tix.FilmList
import com.example.id_tix.filmList
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.id_tix.AuthState
import com.example.id_tix.AuthViewModel
import com.example.id_tix.FilmCard
import com.example.id_tix.R
import com.example.id_tix.ui.theme.BackgroundGray
import com.example.id_tix.ui.theme.PrimaryDark

//data class FilmList(
//    val title: String,
//    val poster:Int,
//)
//
//val filmList = listOf(
//    FilmList("Joker", R.drawable.ic_launcher_poster),
//    FilmList("Star Wars", R.drawable.ic_launcher_poster2),
//    FilmList("Blade Runner 2049", R.drawable.ic_launcher_poster3),
//    FilmList("Spiderman Far From Home", R.drawable.ic_launcher_poster4),
//    FilmList("Avengers Endgame", R.drawable.ic_launcher_poster5),
//    FilmList("La La Land", R.drawable.ic_launcher_poster6),
//    FilmList("The Grand Budapest Hotel", R.drawable.ic_launcher_poster7),
//    FilmList("Minecraft Movie", R.drawable.ic_launcher_poster8),
//    FilmList("Interstellar", R.drawable.ic_launcher_poster9),
//)

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

@Composable
fun HomePage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel){
    Surface(modifier = modifier.fillMaxSize()) {
        Body(props = filmList)
    }
}

@Composable
fun FilmScreen(title: String, films: List<FilmList>, navController: NavController, modifier: Modifier = Modifier) {
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
        }
    }
}