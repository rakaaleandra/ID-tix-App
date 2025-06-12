package com.example.id_tix.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.id_tix.AuthViewModel
import com.example.id_tix.User
import com.example.id_tix.ui.theme.*
import java.text.NumberFormat
import java.util.*

@Composable
fun TopUpPage(
    navController: NavController,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val user by authViewModel.user.observeAsState()
    var selectedAmount by remember { mutableStateOf(0) }
    var customAmount by remember { mutableStateOf("") }
    var showSuccess by remember { mutableStateOf(false) }

    val formatRupiah = remember {
        NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    }

    val topUpAmounts = listOf(
        50000, 100000, 200000, 500000, 1000000, 2000000
    )

    if (showSuccess) {
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(2000)
            navController.popBackStack()
        }
    }

    TopUpPageContent(
        user = user,
        selectedAmount = selectedAmount,
        customAmount = customAmount,
        showSuccess = showSuccess,
        formatRupiah = formatRupiah,
        topUpAmounts = topUpAmounts,
        onAmountSelected = { amount ->
            selectedAmount = amount
            customAmount = ""
        },
        onCustomAmountChanged = { amount ->
            customAmount = amount
            selectedAmount = 0
        },
        onTopUpClick = { finalAmount ->
            if (finalAmount > 0) {
                authViewModel.topUp(finalAmount)
                showSuccess = true
            }
        },
        modifier = modifier
    )
}

@Composable
fun TopUpPageContent(
    user: User?,
    selectedAmount: Int,
    customAmount: String,
    showSuccess: Boolean,
    formatRupiah: NumberFormat,
    topUpAmounts: List<Int>,
    onAmountSelected: (Int) -> Unit,
    onCustomAmountChanged: (String) -> Unit,
    onTopUpClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        // Current Balance Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PrimaryDark),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Saldo Saat Ini",
                    fontSize = 14.sp,
                    color = White.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = formatRupiah.format(user?.balance ?: 0),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = White
                )
            }
        }

        if (showSuccess) {
            // Success Message
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Success",
                        tint = White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Top up berhasil!",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = White
                    )
                }
            }
        } else {
            // Top Up Amount Selection
            Text(
                text = "Pilih Nominal Top Up",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryDark,
                modifier = Modifier.padding(20.dp, 16.dp, 20.dp, 8.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.height(200.dp)
            ) {
                items(topUpAmounts) { amount ->
                    TopUpAmountCard(
                        amount = amount,
                        isSelected = selectedAmount == amount,
                        onClick = { onAmountSelected(amount) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Custom Amount Input
            Text(
                text = "Atau Masukkan Nominal Lain",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryDark,
                modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 8.dp)
            )

            OutlinedTextField(
                value = customAmount,
                onValueChange = onCustomAmountChanged,
                label = { Text("Nominal (Rp)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryDark,
                    focusedLabelColor = PrimaryDark
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            // Top Up Button
            val finalAmount = if (customAmount.isNotEmpty()) {
                customAmount.toIntOrNull() ?: 0
            } else {
                selectedAmount
            }

            Button(
                onClick = { onTopUpClick(finalAmount) },
                enabled = finalAmount > 0,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AccentColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (finalAmount > 0) {
                        "Top Up ${formatRupiah.format(finalAmount)}"
                    } else {
                        "Pilih Nominal"
                    },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = White
                )
            }
        }
    }
}

@Composable
fun TopUpAmountCard(
    amount: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val formatRupiah = remember {
        NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) AccentColor else White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = formatRupiah.format(amount),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) White else PrimaryDark,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopUpPagePreview() {
    IDtixTheme {
        val formatRupiah = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        val sampleUser = User(
            email = "john.doe@example.com",
            balance = 150000
        )

        TopUpPageContent(
            user = sampleUser,
            selectedAmount = 100000,
            customAmount = "",
            showSuccess = false,
            formatRupiah = formatRupiah,
            topUpAmounts = listOf(50000, 100000, 200000, 500000, 1000000, 2000000),
            onAmountSelected = { },
            onCustomAmountChanged = { },
            onTopUpClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopUpSuccessPreview() {
    IDtixTheme {
        val formatRupiah = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        val sampleUser = User(
            email = "john.doe@example.com",
            balance = 250000
        )

        TopUpPageContent(
            user = sampleUser,
            selectedAmount = 0,
            customAmount = "",
            showSuccess = true,
            formatRupiah = formatRupiah,
            topUpAmounts = listOf(50000, 100000, 200000, 500000, 1000000, 2000000),
            onAmountSelected = { },
            onCustomAmountChanged = { },
            onTopUpClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopUpAmountCardPreview() {
    IDtixTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TopUpAmountCard(
                amount = 100000,
                isSelected = false,
                onClick = { }
            )
            TopUpAmountCard(
                amount = 200000,
                isSelected = true,
                onClick = { }
            )
        }
    }
}
