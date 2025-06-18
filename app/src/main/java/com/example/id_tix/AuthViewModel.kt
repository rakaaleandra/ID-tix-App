package com.example.id_tix

import android.os.Message
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.id_tix.api.EmailRequest
import com.example.id_tix.api.LaravelApi
import com.example.id_tix.api.LaravelUser
import com.example.id_tix.api.TopUpRequest
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

data class User(
    val email: String = "",
    val balance: Int = 0
)

data class BookingHistory(
    val id: Int,
    val userId: String, // Add userId to track which user made the booking
    val filmId: Int,
    val filmTitle: String,
    val filmPoster: Int,
    val theaterName: String,
    val showtime: String,
    val bookingDate: String,
    val seatSection: String,
    val quantity: Int,
    val totalPrice: Int,
    val status: BookingStatus
)

enum class BookingStatus {
    COMPLETED,
    CANCELLED,
    UPCOMING
}

class AuthViewModel : ViewModel(){

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState : LiveData<AuthState> = _authState

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _bookingHistory = MutableLiveData<List<BookingHistory>>()
    val bookingHistory: LiveData<List<BookingHistory>> = _bookingHistory

    fun checkAuthStatus(){
        val currentUser = auth.currentUser
        if(currentUser==null){
            _authState.value = AuthState.UnAuthenticated
            _user.value = User(email = "", balance = 0)
        } else {
            val email = currentUser.email
            if (!email.isNullOrEmpty()) {
                _authState.value = AuthState.Authenticated
//                _user.value = User(email = email, balance = 100000)
                viewModelScope.launch {
                    try {
                        val response = LaravelApi.api.checkUser(EmailRequest(email))
                        if (response.isSuccessful) {
                            val laravelUser = response.body()
                            if (laravelUser != null) {
                                _user.value = User(email = laravelUser.email, balance = laravelUser.saldo)
                            }
                        } else {
                            Log.e("LaravelAPI", "User not found in Laravel")
                        }
                    } catch (e: Exception) {
                        Log.e("LaravelAPI", "Error fetching user from Laravel: ${e.localizedMessage}")
                    }
                }
                loadUserBookingHistory()
            } else {
                // Email shouldn't be null, but just in case
                _authState.value = AuthState.UnAuthenticated
                _user.value = User(email = "unknown", balance = 100000)
            }
        }
    }

    init {
        checkAuthStatus()
        // Initialize user with default balance
//        _user.value = User(balance = 100000) // Default balance 100k
        // Initialize empty booking history
//        _bookingHistory.value = emptyList()
    }

    fun login(email : String,password : String){

        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email or Password can't be empty")
            return
        }

        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    _authState.value = AuthState.Authenticated
                    updateUserEmail(email)

                    viewModelScope.launch {
                        try {
                            val response = LaravelApi.api.checkUser(EmailRequest(email))
                            if (response.isSuccessful) {
                                val laravelUser = response.body()
                                if (laravelUser != null) {
                                    // Update user dengan saldo dari Laravel
                                    _user.value = User(email = laravelUser.email, balance = laravelUser.saldo)
                                }
                            } else {
                                Log.e("Laravel", "User not found in Laravel API")
                            }
                        } catch (e: Exception) {
                            Log.e("Laravel", "Error checking user: ${e.localizedMessage}")
                        }
                    }

                    loadUserBookingHistory()
                } else {
                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
                }
            }
    }

    fun signup(email : String,password : String){
        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email or Password can't be empty")
            return
        }
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    _authState.value = AuthState.Authenticated
                    updateUserEmail(email)
                    loadUserBookingHistory()
                    viewModelScope.launch {
                        try {
                            val laravelUser = LaravelUser(
                                name = email.substringBefore("@"),
                                email = email,
                                saldo = 0
                            )
                            val response = LaravelApi.api.createUser(laravelUser)
                            if (response.isSuccessful) {
                                Log.d("Laravel", "User created in Laravel API")
                            } else {
                                Log.e("Laravel", "Failed to create user: ${response.code()}")
                            }
                        } catch (e: Exception) {
                            Log.e("Laravel", "Error: ${e.localizedMessage}")
                        }
                    }
                } else {
                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
                }
            }
    }

    fun topUp(amount: Int) {
        viewModelScope.launch {
            try {
                val email = user.value?.email ?: return@launch
                val response = LaravelApi.api.topUpSaldo(TopUpRequest(email, amount))
                if (response.isSuccessful) {
                    response.body()?.let { laravelUser ->
//                        _user.value = User(email = laravelUser.email, balance = laravelUser.saldo)
                        val currentUser = _user.value ?: User()
                        _user.value = currentUser.copy(balance = currentUser.balance + amount)
                    }
                } else {
                    Log.e("TopUp", "Top up failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("TopUp", "Top up error: ${e.localizedMessage}")
            }
        }
    }

    fun signout(){
        auth.signOut()
        _authState.value = AuthState.UnAuthenticated
        _bookingHistory.value = emptyList() // Clear booking history on logout
    }

//    fun topUp(amount: Int) {
//        val currentUser = _user.value ?: User()
//        _user.value = currentUser.copy(balance = currentUser.balance + amount)
//    }

    fun updateUserEmail(email: String) {
        val currentUser = _user.value ?: User()
        _user.value = currentUser.copy(email = email)
    }

    fun addBooking(
        filmId: Int,
        filmTitle: String,
        filmPoster: Int,
        theaterName: String,
        showtime: String,
        seatSection: String,
        quantity: Int,
        totalPrice: Int
    ) {
        val currentUser = _user.value
        if (currentUser != null && currentUser.email.isNotEmpty()) {
            val currentHistory = _bookingHistory.value ?: emptyList()
            val newBooking = BookingHistory(
                id = (currentHistory.maxOfOrNull { it.id } ?: 0) + 1,
                userId = currentUser.email,
                filmId = filmId,
                filmTitle = filmTitle,
                filmPoster = filmPoster,
                theaterName = theaterName,
                showtime = showtime,
                bookingDate = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date()),
                seatSection = seatSection,
                quantity = quantity,
                totalPrice = totalPrice,
                status = BookingStatus.UPCOMING
            )

            _bookingHistory.value = currentHistory + newBooking

            // Deduct balance
            _user.value = currentUser.copy(balance = currentUser.balance - totalPrice)
        }
    }

    private fun loadUserBookingHistory() {
        val currentUser = _user.value
        if (currentUser != null && currentUser.email.isNotEmpty()) {
            // In a real app, this would load from a database
            // For demo purposes, we'll load sample data only for specific users
            val userBookings = when (currentUser.email) {
                "demo@example.com" -> getSampleBookingsForUser(currentUser.email)
                "test@example.com" -> getSampleBookingsForUser(currentUser.email)
                else -> emptyList()
            }
            _bookingHistory.value = userBookings
        } else {
            _bookingHistory.value = emptyList()
        }
    }

    private fun getSampleBookingsForUser(userEmail: String): List<BookingHistory> {
        // Sample bookings for demo users
        return when (userEmail) {
            "demo@example.com" -> listOf(
                BookingHistory(
                    id = 1,
                    userId = userEmail,
                    filmId = 1,
                    filmTitle = "Joker",
                    filmPoster = R.drawable.ic_launcher_poster,
                    theaterName = "CGV Central Park",
                    showtime = "19:00",
                    bookingDate = "2024-01-15",
                    seatSection = "Middle",
                    quantity = 2,
                    totalPrice = 100000,
                    status = BookingStatus.COMPLETED
                ),
                BookingHistory(
                    id = 2,
                    userId = userEmail,
                    filmId = 5,
                    filmTitle = "Avengers Endgame",
                    filmPoster = R.drawable.ic_launcher_poster5,
                    theaterName = "XXI Plaza Senayan",
                    showtime = "20:00",
                    bookingDate = "2024-01-10",
                    seatSection = "Back",
                    quantity = 1,
                    totalPrice = 50000,
                    status = BookingStatus.COMPLETED
                )
            )
            "test@example.com" -> listOf(
                BookingHistory(
                    id = 3,
                    userId = userEmail,
                    filmId = 9,
                    filmTitle = "Interstellar",
                    filmPoster = R.drawable.ic_launcher_poster9,
                    theaterName = "CGV Pacific Place",
                    showtime = "14:15",
                    bookingDate = "2024-01-20",
                    seatSection = "Middle",
                    quantity = 2,
                    totalPrice = 100000,
                    status = BookingStatus.UPCOMING
                )
            )
            else -> emptyList()
        }
    }
}

sealed class AuthState{
    object Authenticated : AuthState()
    object UnAuthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}
