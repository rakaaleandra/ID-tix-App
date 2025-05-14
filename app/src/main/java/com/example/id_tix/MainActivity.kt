package com.example.id_tix

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.id_tix.ui.theme.IDtixTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IDtixTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {Header()}) { innerPadding ->
                    HomePage(modifier = Modifier.padding(innerPadding))
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
fun Header(){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.background(Color(0xff1f293d)).fillMaxWidth().padding(15.dp,35.dp,15.dp,15.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_logo),
            contentDescription = "Logo",
            modifier = Modifier.size(30.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_vector),
                contentDescription = "vector",
                modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Now Showing",
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
        Text(text = "Profil",color = Color.White, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun Body(props: List<FilmList>){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(15.dp,0.dp,15.dp,15.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp),
        horizontalArrangement = Arrangement.spacedBy(30.dp)
    ) {
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
                Text(
                    text = "Now Showing",
                    style = TextStyle(
                        fontSize = 30.sp
                    ),
                    modifier = Modifier.padding(15.dp),
                    fontWeight = FontWeight.Bold,
                )
                Body(props =  filmList)
            }
        }
    }
}