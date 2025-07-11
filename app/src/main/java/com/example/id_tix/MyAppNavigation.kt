package com.example.id_tix
//import com.example.id_tix.pages.HomePage

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.id_tix.pages.*

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel, navHostController: NavHostController){
 val navController = navHostController
 NavHost(navController = navController, startDestination = "now_showing", builder = {
  composable("signup"){
   SignupPage(modifier, navController,authViewModel)
  }
  composable("login"){
   LoginPage(modifier, navController,authViewModel)
  }
  composable("profile") {
   ProfilePage(navController = navController, authViewModel = authViewModel, modifier = modifier)
  }
  composable("now_showing") {
   FilmScreen("Now Showing", filmList, navController, modifier)
  }
  composable("coming_soon") {
   FilmScreen("Coming Soon", comingSoonList, navController, modifier)
  }
  composable("film_detail/{filmId}") { backStackEntry ->
   val filmId = backStackEntry.arguments?.getString("filmId")?.toIntOrNull()
   val allFilms = filmList + comingSoonList
   val film = allFilms.find { it.id == filmId }
   film?.let { FilmDetailScreen(it, navController, modifier) }
  }
  composable("schedule/{filmId}") { backStackEntry ->
   val filmId = backStackEntry.arguments?.getString("filmId")?.toIntOrNull() ?: 1
   SchedulePage(filmId, navController, modifier)
  }
  composable("seat_selection/{filmId}/{theaterId}/{showtime}") { backStackEntry ->
   val filmId = backStackEntry.arguments?.getString("filmId")?.toIntOrNull() ?: 1
   val theaterId = backStackEntry.arguments?.getString("theaterId")?.toIntOrNull() ?: 1
   val showtime = backStackEntry.arguments?.getString("showtime") ?: "15:10"
   SeatSelectionPage(filmId, theaterId, showtime, navController, modifier)
  }
  composable("payment/{filmId}/{theaterId}/{showtime}/{seatSection}/{quantity}/{totalPrice}") { backStackEntry ->
   val filmId = backStackEntry.arguments?.getString("filmId")?.toIntOrNull() ?: 1
   val theaterId = backStackEntry.arguments?.getString("theaterId")?.toIntOrNull() ?: 1
   val showtime = backStackEntry.arguments?.getString("showtime") ?: "15:10"
   val seatSection = backStackEntry.arguments?.getString("seatSection") ?: "Tengah"
   val quantity = backStackEntry.arguments?.getString("quantity")?.toIntOrNull() ?: 1
   val totalPrice = backStackEntry.arguments?.getString("totalPrice")?.toIntOrNull() ?: 50000
   PaymentPage(filmId, theaterId, showtime, seatSection, quantity, totalPrice, navController, authViewModel, modifier)
  }
  composable("ticket/{codePemesanan}") { backStackEntry ->
//   val filmId = backStackEntry.arguments?.getString("filmId")?.toIntOrNull() ?: 1
//   val theaterName = backStackEntry.arguments?.getString("theaterName") ?: "CGV Central Park"
//   val showtime = backStackEntry.arguments?.getString("showtime") ?: "15:10"
//   val totalPrice = backStackEntry.arguments?.getString("totalPrice")?.toIntOrNull() ?: 50000
   val codePemesanan = backStackEntry.arguments?.getString("codePemesanan") ?: "error"
   TicketPage(codePemesanan,authViewModel , navController, modifier)
  }
//  composable("ticket/{filmId}/{theaterName}/{showtime}/{totalPrice}") { backStackEntry ->
//   val filmId = backStackEntry.arguments?.getString("filmId")?.toIntOrNull() ?: 1
//   val theaterName = backStackEntry.arguments?.getString("theaterName") ?: "CGV Central Park"
//   val showtime = backStackEntry.arguments?.getString("showtime") ?: "15:10"
//   val totalPrice = backStackEntry.arguments?.getString("totalPrice")?.toIntOrNull() ?: 50000
//   TicketPage(filmId, theaterName, showtime, totalPrice, navController, modifier)
//  }
  composable("history") {
   HistoryPage(navController, authViewModel, modifier)
  }
  composable("topup") {
   TopUpPage(navController, authViewModel, modifier)
  }
  composable("sukses") {
   SuksesBayar(navController, modifier)
  }
 })
}
