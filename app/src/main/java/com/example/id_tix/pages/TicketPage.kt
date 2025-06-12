package com.example.id_tix.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.id_tix.R
import com.example.id_tix.filmList
import com.example.id_tix.theaterList
import com.example.id_tix.ui.theme.*
import java.text.NumberFormat
import java.util.*

@Composable
fun TicketPage(
    filmId: Int,
    theaterId: Int,
    showtime: String,
    totalPrice: Int,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val film = filmList.find { it.id == filmId } ?: filmList[0]
    val theater = theaterList.find { it.id == theaterId } ?: theaterList[0]

    // Format currency to Rupiah
    val formatRupiah = remember {
        NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1F293D))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header with close button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Your ticket",
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            IconButton(onClick = {
                navController.navigate("now_showing") {
                    popUpTo("now_showing") {
                        inclusive = false
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // QR Code with image
        Surface(
            modifier = Modifier.size(280.dp),
            shape = RoundedCornerShape(20.dp),
            color = White
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_qrcode),
                contentDescription = "QR Code",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Show this code to the gatekeeper at the cinema",
            color = Color.Gray,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(60.dp))

        // Movie title
        Text(
            text = film.title,
            color = White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Ticket details - now using actual data
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TicketDetailRow("Cinema", theater.name)
            TicketDetailRow("Time", showtime)
            TicketDetailRow("Cost", "${formatRupiah.format(totalPrice)} (paid)")
        }

        Spacer(modifier = Modifier.weight(1f))

        // Back to home button
        Button(
            onClick = {
                navController.navigate("now_showing") {
                    popUpTo("now_showing") {
                        inclusive = false
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AccentColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Kembali ke Home",
                color = White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun TicketDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 16.sp
        )
        Text(
            text = value,
            color = White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
