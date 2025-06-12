package com.example.id_tix.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.id_tix.filmList
import com.example.id_tix.theaterList
import com.example.id_tix.ui.theme.*
import java.text.NumberFormat
import java.util.*

@Composable
fun SeatSelectionHeader(navController: NavController) {
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
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = White,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Session / Select Seats",
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SeatSelectionPage(
    filmId: Int,
    theaterId: Int,
    showtime: String,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val film = filmList.find { it.id == filmId } ?: filmList[0]
    val theater = theaterList.find { it.id == theaterId } ?: theaterList[0]

    var selectedSection by remember { mutableStateOf<String?>(null) }
    var selectedQuantity by remember { mutableStateOf(1) }

    val seatPrice = 50000

    // Format currency to Rupiah
    val formatRupiah = remember {
        NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1F293D))
    ) {
        // Cinema and Movie Info
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = theater.name,
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = film.title,
                color = Color.Gray,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Time Info
            Surface(
                modifier = Modifier.fillMaxWidth(0.5f),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFF2A3A59)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "Time",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = showtime,
                        color = White,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Screen indicator
            Text(
                text = "SCREEN",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Screen line
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
            ) {
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 2f
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }

        // Seat Section Selection
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Select Seat Area",
                color = White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Seat sections
            val sections = listOf("Front", "Middle", "Back")

            sections.forEach { section ->
                SeatSectionCard(
                    section = section,
                    isSelected = selectedSection == section,
                    onSelect = { selectedSection = section }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Quantity selector
            if (selectedSection != null) {
                Text(
                    text = "Number of Tickets",
                    color = White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { if (selectedQuantity > 1) selectedQuantity-- },
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                Color(0xFF2A3A59),
                                RoundedCornerShape(8.dp)
                            )
                    ) {
                        Text(
                            text = "-",
                            color = White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = selectedQuantity.toString(),
                        color = White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )

                    IconButton(
                        onClick = { if (selectedQuantity < 6) selectedQuantity++ },
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                Color(0xFF2A3A59),
                                RoundedCornerShape(8.dp)
                            )
                    ) {
                        Text(
                            text = "+",
                            color = White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
        }

        // Continue button
        if (selectedSection != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                val totalPrice = seatPrice * selectedQuantity
                Button(
                    onClick = {
                        navController.navigate("payment/$filmId/$theaterId/$showtime/$selectedSection/$selectedQuantity/$totalPrice")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AccentColor)
                ) {
                    Text(
                        text = "Continue to Payment • ${formatRupiah.format(totalPrice)}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun SeatSectionCard(
    section: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) AccentColor.copy(alpha = 0.2f) else Color(0xFF2A3A59)
        ),
        border = if (isSelected) BorderStroke(2.dp, AccentColor) else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = section,
                color = if (isSelected) AccentColor else White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            if (isSelected) {
                Surface(
                    modifier = Modifier.size(24.dp),
                    shape = CircleShape,
                    color = AccentColor
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "✓",
                            color = White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SeatSelectionHeaderPreview() {
    IDtixTheme {
        SeatSelectionHeader(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun SeatSelectionPagePreview() {
    IDtixTheme {
        SeatSelectionPage(
            filmId = 1,
            theaterId = 1,
            showtime = "19:00",
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SeatSectionCardPreview() {
    IDtixTheme {
        Column(
            modifier = Modifier
                .background(Color(0xFF1F293D))
                .padding(16.dp)
        ) {
            SeatSectionCard(
                section = "Front",
                isSelected = false,
                onSelect = { }
            )
            Spacer(modifier = Modifier.height(8.dp))
            SeatSectionCard(
                section = "Middle",
                isSelected = true,
                onSelect = { }
            )
            Spacer(modifier = Modifier.height(8.dp))
            SeatSectionCard(
                section = "Back",
                isSelected = false,
                onSelect = { }
            )
        }
    }
}
