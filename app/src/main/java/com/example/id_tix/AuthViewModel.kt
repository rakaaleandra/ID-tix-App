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

class AuthViewModel : ViewModel(){
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState : LiveData<AuthState> = _authState

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus(){
        val currentUser = auth.currentUser
        if (currentUser == null){
            _authState.value = AuthState.UnAuthenticated
            _user.value = User(email = "", balance = 100000)
        } else {
            _authState.value = AuthState.Authenticated
            _user.value = User(email = currentUser.email ?: "", balance = 100000)
        }
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
//                    _authState.value = AuthState.Authenticated(email)
                    updateUserEmail(email)
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
//                    _authState.value = AuthState.Authenticated(email)
                    updateUserEmail(email)
                } else {
                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
                }
            }
    }

    fun signout(){
        auth.signOut()
        _authState.value = AuthState.UnAuthenticated
    }

    fun topUp(amount: Int) {
        val currentUser = _user.value ?: User()
        _user.value = currentUser.copy(balance = currentUser.balance + amount)
    }

    fun updateUserEmail(email: String) {
        val currentUser = _user.value ?: User()
        _user.value = currentUser.copy(email = email)
    }
}

sealed class AuthState{
    object Authenticated : AuthState()
//    data class Authenticated(val email: String) : AuthState()
    object UnAuthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}
