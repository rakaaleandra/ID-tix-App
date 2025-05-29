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
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import kotlinx.coroutines.delay
import com.example.id_tix.R.drawable.ic_launcher_splashbackground
import com.example.id_tix.R.drawable.ic_launcher_logo
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Brush

@Composable
fun SplashScreen(onAnimationEnd: () -> Unit){
    val context = LocalContext.current
    val window = (context as Activity).window

    window.statusBarColor = Color(0xFF1F293D).toArgb()
    WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

    var isVisible by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = true) {
        delay(2000)
        isVisible = false
        delay(1000)
        onAnimationEnd()
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFF1F293D)),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(ic_launcher_splashbackground),
            contentDescription = "null",
            modifier = Modifier.fillMaxSize()
        )
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
            exit = fadeOut(animationSpec = tween(durationMillis = 1000))
        ) {
            Image(
                painter = painterResource(ic_launcher_logo),
                contentDescription = "Splash Logo",
                modifier = Modifier
                    .size(120.dp)
            )
        }
    }
}