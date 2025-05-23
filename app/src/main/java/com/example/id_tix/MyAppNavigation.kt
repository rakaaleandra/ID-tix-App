package com.example.id_tix
import com.example.id_tix.pages.HomePage

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.id_tix.pages.LoginPage
import com.example.id_tix.pages.SignupPage

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel, navHostController: NavHostController){
// val navController = rememberNavController()
 val navController = navHostController
 NavHost(navController = navController, startDestination = "Home", builder = {
  composable("Home"){
   HomePage(modifier, navController,authViewModel)
  }
  composable("Signup"){
   SignupPage(modifier, navController,authViewModel)
  }
  composable("Login"){
   LoginPage(modifier, navController,authViewModel)
  }
 })
}