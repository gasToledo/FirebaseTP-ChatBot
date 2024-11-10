package com.tp.firebase.tp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tp.firebase.tp.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : ViewModel() {


    fun signUp(email: String, password: String, name: String) {

        Log.d("SignUpViewModel", "signUp: $email $password $name")

        viewModelScope.launch(Dispatchers.IO) {
            auth.createUserWithEmailAndPassword(
                email,
                password
            )
                .addOnSuccessListener {
                    it.user?.let { user ->
                        db.collection("user").add(
                            User(
                                user.uid,
                                name,
                                email
                            )
                        )
                    }
                    Log.d("SignUpViewModel", "Usuario registrado correctamente")
                }
                .addOnFailureListener {
                    Log.d("SignUpViewModel", "Error al registrar usuario")
                }
        }
    }
}