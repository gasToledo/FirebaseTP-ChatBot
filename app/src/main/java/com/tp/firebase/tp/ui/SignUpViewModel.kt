package com.tp.firebase.tp.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.tp.firebase.tp.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val analytics: FirebaseAnalytics,
    private val db: FirebaseFirestore,
    private val crashlytics: FirebaseCrashlytics
) : ViewModel() {


    fun signUp(email: String, password: String, name: String) {

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
                    val parameters = Bundle().apply {
                        this.putString("registro", "Se ha registrado un nuevo usuario.")
                    }
                    analytics.setDefaultEventParameters(parameters)
                }
                .addOnFailureListener {
                    crashlytics.setCustomKey("registro", "Error al registrar usuario")
                    crashlytics.recordException(it)
                }
        }
    }
}