package com.example.id_tix.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavController
import com.example.id_tix.FilmList
import com.example.id_tix.ui.theme.*

@Composable
fun DetailHeader(navController: NavHostController) {
    Surface(color = PrimaryDark, shadowElevation = 4.dp) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 6.dp)
                .statusBarsPadding()
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = White,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Film Detail",
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun RegistrationHeader(navController: NavHostController) {
    Surface(color = PrimaryDark, shadowElevation = 4.dp) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 6.dp)
                .statusBarsPadding()
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
fun TicketHeader(navController: NavHostController) {
    Surface(color = PrimaryDark, shadowElevation = 4.dp) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 6.dp)
                .statusBarsPadding()
        ) {
            IconButton(onClick = {
                navController.navigate("now_showing") {
                    popUpTo("now_showing") {
                        inclusive = false
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = White,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Your Ticket",
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ProfileHeader(navController: NavController) {
    Surface(color = PrimaryDark, shadowElevation = 4.dp) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 6.dp)
                .statusBarsPadding()
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = White,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Profile",
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TopUpHeader(navController: NavController) {
    Surface(color = PrimaryDark, shadowElevation = 4.dp) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 6.dp)
                .statusBarsPadding()
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = White,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Top Up Saldo",
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun FilmDetailScreen(film: FilmList, navController: NavController, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundGray)
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = film.poster),
            contentDescription = "Movie poster for ${film.title}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = White)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = film.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryDark
                )

                Spacer(modifier = Modifier.height(16.dp))

                DetailRow("Duration", "${film.duration} minutes")
                DetailRow("Director", film.director)
                DetailRow("Genre", film.genre)
                DetailRow("Producers", film.producers)
                DetailRow("Production Company", film.productionCompany)
                DetailRow("Cast", film.casts)

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Synopsis",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryDark
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = film.synopsis,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Book Tickets Button
                Button(
                    onClick = { navController.navigate("schedule/${film.id}") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryDark),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Book Tickets",
                        color = White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = PrimaryDark
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}
