package com.example.id_tix

import android.os.Message
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

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
            _user.value = User(email = "", balance = 100000)
        } else {
            val email = currentUser.email
            if (!email.isNullOrEmpty()) {
                _authState.value = AuthState.Authenticated
                _user.value = User(email = email, balance = 100000)
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
                } else {
                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
                }
            }
    }

    fun signout(){
        auth.signOut()
        _authState.value = AuthState.UnAuthenticated
        _bookingHistory.value = emptyList() // Clear booking history on logout
    }

    fun topUp(amount: Int) {
        val currentUser = _user.value ?: User()
        _user.value = currentUser.copy(balance = currentUser.balance + amount)
    }

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
