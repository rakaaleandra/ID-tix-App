package com.example.id_tix.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.id_tix.AuthViewModel
import com.example.id_tix.BookingHistory
import com.example.id_tix.BookingStatus
import com.example.id_tix.R
import com.example.id_tix.ui.theme.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HistoryHeader(navController: NavController) {
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
                text = "Riwayat Transaksi",
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun HistoryPage(
    navController: NavController,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val bookingHistory by authViewModel.bookingHistory.observeAsState(emptyList())
    var selectedFilter by remember { mutableStateOf("All") }

    val filteredHistory = when (selectedFilter) {
        "Completed" -> bookingHistory.filter { it.status == BookingStatus.COMPLETED }
        "Upcoming" -> bookingHistory.filter { it.status == BookingStatus.UPCOMING }
        "Cancelled" -> bookingHistory.filter { it.status == BookingStatus.CANCELLED }
        else -> bookingHistory
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        // Filter Tabs
        FilterTabs(
            selectedFilter = selectedFilter,
            onFilterSelected = { selectedFilter = it },
            modifier = Modifier.padding(20.dp)
        )

        if (filteredHistory.isEmpty()) {
            // Empty State
            EmptyHistoryState(
                filter = selectedFilter,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            )
        } else {
            // History List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredHistory) { booking ->
                    BookingHistoryCard(
                        booking = booking,
                        onCardClick = {
                            // Navigate to ticket detail or film detail
                            if (booking.status == BookingStatus.UPCOMING) {
                                navController.navigate("ticket/${booking.filmId}/1/${booking.showtime}/${booking.totalPrice}")
                            } else {
                                navController.navigate("film_detail/${booking.filmId}")
                            }
                        }
                    )
                }

                // Bottom spacing
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
fun FilterTabs(
    selectedFilter: String,
    onFilterSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val filters = listOf("All", "Completed", "Upcoming", "Cancelled")

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.forEach { filter ->
            FilterChip(
                selected = selectedFilter == filter,
                onClick = { onFilterSelected(filter) },
                label = {
                    Text(
                        text = filter,
                        fontSize = 14.sp,
                        fontWeight = if (selectedFilter == filter) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = AccentColor,
                    selectedLabelColor = White,
                    containerColor = White,
                    labelColor = PrimaryDark
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = selectedFilter == filter,
                    borderColor = if (selectedFilter == filter) AccentColor else Color.Gray,
                    selectedBorderColor = AccentColor
                )
            )
        }
    }
}

@Composable
fun BookingHistoryCard(
    booking: BookingHistory,
    onCardClick: () -> Unit
) {
    val formatRupiah = remember {
        NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    }

    val dateFormat = remember {
        SimpleDateFormat("dd MMM yyyy", Locale("in", "ID"))
    }

    val bookingDate = try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = inputFormat.parse(booking.bookingDate)
        dateFormat.format(date ?: Date())
    } catch (e: Exception) {
        booking.bookingDate
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onCardClick,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Movie Poster
            Image(
                painter = painterResource(id = booking.filmPoster),
                contentDescription = "Movie poster for ${booking.filmTitle}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp, 120.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Booking Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Status Badge
                StatusBadge(status = booking.status)

                Spacer(modifier = Modifier.height(8.dp))

                // Movie Title
                Text(
                    text = booking.filmTitle,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryDark,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Theater and Time
                Text(
                    text = booking.theaterName,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "${booking.showtime} • ${booking.seatSection}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Booking Date
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Date",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = bookingDate,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Price and Quantity
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "${booking.quantity} tiket",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = formatRupiah.format(booking.totalPrice),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDark
                    )
                }
            }
        }
    }
}

@Composable
fun StatusBadge(status: BookingStatus) {
    val (backgroundColor, textColor, text) = when (status) {
        BookingStatus.COMPLETED -> Triple(
            Color(0xFF4CAF50).copy(alpha = 0.1f),
            Color(0xFF4CAF50),
            "Selesai"
        )
        BookingStatus.UPCOMING -> Triple(
            AccentColor.copy(alpha = 0.1f),
            AccentColor,
            "Akan Datang"
        )
        BookingStatus.CANCELLED -> Triple(
            Color(0xFFF44336).copy(alpha = 0.1f),
            Color(0xFFF44336),
            "Dibatalkan"
        )
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = textColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun EmptyHistoryState(
    filter: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = when (filter) {
                "Completed" -> Icons.Default.CheckCircle
                "Upcoming" -> Icons.Default.Schedule
                "Cancelled" -> Icons.Default.Cancel
                else -> Icons.Default.History
            },
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = when (filter) {
                "Completed" -> "Belum ada transaksi selesai"
                "Upcoming" -> "Belum ada tiket yang akan datang"
                "Cancelled" -> "Belum ada transaksi dibatalkan"
                else -> "Belum ada riwayat transaksi"
            },
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )

        Text(
            text = when (filter) {
                "Completed" -> "Transaksi yang sudah selesai akan muncul di sini"
                "Upcoming" -> "Tiket yang belum digunakan akan muncul di sini"
                "Cancelled" -> "Transaksi yang dibatalkan akan muncul di sini"
                else -> "Mulai pesan tiket untuk melihat riwayat"
            },
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyHistoryStatePreview() {
    IDtixTheme {
        EmptyHistoryState(
            filter = "All",
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        )
    }
}
