package com.example.id_tix.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.id_tix.*
import com.example.id_tix.ui.theme.*

@Composable
fun ScheduleHeader(navController: NavController) {
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
                text = "Pilih Kota",
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SchedulePage(filmId: Int, navController: NavController, modifier: Modifier = Modifier) {
    var selectedCity by remember { mutableStateOf("Pilih Kota") }
    var showCityDropdown by remember { mutableStateOf(false) }

    val filteredTheaters = if (selectedCity == "Pilih Kota") {
        emptyList()
    } else {
        theaterList.filter { it.city == selectedCity }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PrimaryDark)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Dropdown Pilih Kota
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showCityDropdown = !showCityDropdown }
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(12.dp)
                    ),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFF2A3A59)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = AccentColor,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = selectedCity,
                            color = White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown",
                        tint = White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Dropdown Menu
            DropdownMenu(
                expanded = showCityDropdown,
                onDismissRequest = { showCityDropdown = false },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(
                        Color(0xFF2A3A59),
                        RoundedCornerShape(12.dp)
                    )
                    .clip(RoundedCornerShape(12.dp))
            ) {
                cityList.forEach { city ->
                    DropdownMenuItem(
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = if (selectedCity == city.name) AccentColor else Color.Gray,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = city.name,
                                    color = if (selectedCity == city.name) AccentColor else White,
                                    fontSize = 14.sp,
                                    fontWeight = if (selectedCity == city.name) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        },
                        onClick = {
                            selectedCity = city.name
                            showCityDropdown = false
                        },
                        modifier = Modifier.background(
                            if (selectedCity == city.name)
                                AccentColor.copy(alpha = 0.1f)
                            else
                                Color.Transparent
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Info text ketika belum memilih kota
        if (selectedCity == "Pilih Kota") {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Silakan pilih kota terlebih dahulu",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "untuk melihat daftar bioskop",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        } else {
            // Header untuk daftar theater
            Text(
                text = "Bioskop di $selectedCity",
                color = White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(20.dp, 16.dp)
            )

            // Daftar Theater
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredTheaters) { theater ->
                    TheaterScheduleItem(theater = theater, filmId = filmId, navController = navController)
                }

                // Spacer untuk bottom padding
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
fun TheaterScheduleItem(
    theater: Theater,
    filmId: Int,
    navController: NavController
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A3A59)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = theater.name,
                color = White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(theater.showtimes) { showtime ->
                    ShowtimeButton(
                        time = showtime,
                        onClick = {
                            navController.navigate("seat_selection/$filmId/${theater.id}/$showtime")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ShowtimeButton(
    time: String,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.clickable {
            isPressed = true
            onClick()
        },
        shape = RoundedCornerShape(8.dp),
        color = if (isPressed) AccentColor else Color.Transparent,
        border = BorderStroke(
            width = 1.dp,
            color = if (isPressed) AccentColor else Color.Gray
        )
    ) {
        Text(
            text = time,
            color = if (isPressed) White else Color.Gray,
            fontSize = 14.sp,
            fontWeight = if (isPressed) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.padding(16.dp, 10.dp)
        )
    }
}
