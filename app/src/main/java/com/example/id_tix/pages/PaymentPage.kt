package com.example.id_tix.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.id_tix.AuthViewModel
import com.example.id_tix.R
import com.example.id_tix.filmList
import com.example.id_tix.theaterList
import com.example.id_tix.ui.theme.*
import java.text.NumberFormat
import java.util.*

@Composable
fun PaymentHeader(navController: NavController) {
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
                text = "Payment",
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun PaymentPage(
    filmId: Int,
    theaterId: Int,
    showtime: String,
    seatSection: String,
    quantity: Int,
    totalPrice: Int,
    navController: NavController,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val film = filmList.find { it.id == filmId } ?: filmList[0]
    val theater = theaterList.find { it.id == theaterId } ?: theaterList[0]
    val user by authViewModel.user.observeAsState()

    var isProcessing by remember { mutableStateOf(false) }

    // Format currency to Rupiah
    val formatRupiah = remember {
        NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    }

    val walletBalance = user?.balance ?: 0

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1F293D))
            .verticalScroll(rememberScrollState())
    ) {
        // Order Summary
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF2A3A59))
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Order Summary",
                    color = White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OrderDetailRow("Movie", film.title)
                OrderDetailRow("Cinema", theater.name)
                OrderDetailRow("Time", showtime)
                OrderDetailRow("Seat Area", seatSection)
                OrderDetailRow("Number of Tickets", "$quantity tickets")

                Divider(
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 12.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total Payment",
                        color = White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = formatRupiah.format(totalPrice),
                        color = AccentColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Wallet Balance Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF2A3A59))
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountBalanceWallet,
                        contentDescription = "Wallet",
                        tint = AccentColor,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Wallet Balance",
                        color = White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = formatRupiah.format(walletBalance),
                    color = if (walletBalance >= totalPrice) Color(0xFF4CAF50) else AccentColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                if (walletBalance < totalPrice) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Insufficient balance. Please top up your wallet.",
                        color = AccentColor,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Pay Button or Top Up Button
        if (walletBalance >= totalPrice) {
            Button(
                onClick = {
                    isProcessing = true
                    // Add booking to user's history
                    authViewModel.addBooking(
                        filmId = filmId,
                        filmTitle = film.title,
                        filmPoster = film.poster,
                        theaterName = theater.name,
                        showtime = showtime,
                        seatSection = seatSection,
                        quantity = quantity,
                        totalPrice = totalPrice
                    )
                    // Navigate to ticket page
                    navController.navigate("ticket/$filmId/$theaterId/$showtime/$totalPrice") {
                        popUpTo("now_showing") {
                            inclusive = false
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AccentColor),
                enabled = !isProcessing
            ) {
                if (isProcessing) {
                    CircularProgressIndicator(
                        color = White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = "Pay Now",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } else {
            // Top Up Button
            Button(
                onClick = {
                    navController.navigate("topup")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A3A59)),
                border = BorderStroke(1.dp, AccentColor)
            ) {
                Text(
                    text = "Top Up Wallet",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AccentColor
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Disabled Pay Button
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF37474F)),
                enabled = false
            ) {
                Text(
                    text = "Pay Now",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun OrderDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 14.sp
        )
        Text(
            text = value,
            color = White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentHeaderPreview() {
    IDtixTheme {
        PaymentHeader(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentPagePreview() {
    IDtixTheme {
        PaymentPage(
            filmId = 1,
            theaterId = 1,
            showtime = "19:00",
            seatSection = "Middle",
            quantity = 2,
            totalPrice = 100000,
            navController = rememberNavController(),
            authViewModel = AuthViewModel()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentPageInsufficientBalancePreview() {
    IDtixTheme {
        PaymentPage(
            filmId = 1,
            theaterId = 1,
            showtime = "19:00",
            seatSection = "Middle",
            quantity = 4,
            totalPrice = 200000,
            navController = rememberNavController(),
            authViewModel = AuthViewModel()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OrderDetailRowPreview() {
    IDtixTheme {
        Column(
            modifier = Modifier
                .background(Color(0xFF2A3A59))
                .padding(16.dp)
        ) {
            OrderDetailRow("Movie", "Joker")
            OrderDetailRow("Cinema", "CGV Central Park")
            OrderDetailRow("Time", "19:00")
            OrderDetailRow("Seat Area", "Middle")
            OrderDetailRow("Number of Tickets", "2 tickets")
        }
    }
}
