package com.tp.firebase.tp.ui

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db : FirebaseFirestore
) : ViewModel() {

    /*val currentUser = auth.currentUser
    private val docRef: DocumentReference? by lazy {
        currentUser?.let { db.collection("usuario").document(it.uid) }
    }

    private val _username = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> = _username

    init {
        getUsername()
    }


    fun getUsername(){
        viewModelScope.launch {
            try {
                val documentSnaptshot = docRef?.get()?.await()
                _username.value = documentSnaptshot?.getString("username")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("LoginViewModel", "Error fetching username: ${e.message}")
            }
        }
    }*/
}



