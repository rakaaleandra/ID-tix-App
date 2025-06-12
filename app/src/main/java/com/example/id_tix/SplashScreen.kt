package com.example.id_tix

import android.app.Activity
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import kotlinx.coroutines.delay
import com.example.id_tix.R.drawable.ic_launcher_splashbackground
import com.example.id_tix.R.drawable.ic_launcher_logo
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color.Companion.White
import com.example.id_tix.ui.theme.AccentColor
import com.example.id_tix.ui.theme.IDtixTheme

@Composable
fun SplashScreen(onAnimationEnd: () -> Unit){
    val context = LocalContext.current

    // Only set status bar if we have an Activity context
    if (context is Activity) {
        val window = context.window
        // Set status bar color using WindowInsetsController for newer APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                0,
                android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            @Suppress("DEPRECATION")
            window.statusBarColor = Color(0xFF1F293D).toArgb()
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
        }
    }

    SplashScreenContent(onAnimationEnd = onAnimationEnd)
}

@Composable
fun SplashScreenContent(onAnimationEnd: () -> Unit) {
    var logoVisible by remember { mutableStateOf(false) }
    var textVisible by remember { mutableStateOf(false) }
    var fadeOut by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        // Logo animation
        delay(500)
        logoVisible = true

        // Text animation
        delay(800)
        textVisible = true

        // Hold for a moment
        delay(1200)

        // Fade out
        fadeOut = true
        delay(800)

        onAnimationEnd()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1F293D),
                        Color(0xFF2A3A59)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ){
        // Background pattern
        Image(
            painter = painterResource(ic_launcher_splashbackground),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.3f
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo with animation
            AnimatedVisibility(
                visible = logoVisible && !fadeOut,
                enter = fadeIn(animationSpec = tween(durationMillis = 800)) +
                        scaleIn(animationSpec = tween(durationMillis = 800)),
                exit = fadeOut(animationSpec = tween(durationMillis = 800))
            ) {
                Image(
                    painter = painterResource(ic_launcher_logo),
                    contentDescription = "ID-Tix Logo",
                    modifier = Modifier.size(120.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // App name with animation
            AnimatedVisibility(
                visible = textVisible && !fadeOut,
                enter = fadeIn(animationSpec = tween(durationMillis = 600)) +
                        slideInVertically(
                            animationSpec = tween(durationMillis = 600),
                            initialOffsetY = { it / 2 }
                        ),
                exit = fadeOut(animationSpec = tween(durationMillis = 800))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "ID-Tix",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = White
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Your Movie Ticket Partner",
                        fontSize = 16.sp,
                        color = White.copy(alpha = 0.8f),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Loading indicator at bottom
        AnimatedVisibility(
            visible = textVisible && !fadeOut,
            enter = fadeIn(animationSpec = tween(durationMillis = 600, delayMillis = 400)),
            exit = fadeOut(animationSpec = tween(durationMillis = 800)),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 60.dp)
            ) {
                CircularProgressIndicator(
                    color = AccentColor,
                    strokeWidth = 3.dp,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Loading...",
                    fontSize = 14.sp,
                    color = White.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    IDtixTheme {
        SplashScreenContent(onAnimationEnd = { })
    }
}
