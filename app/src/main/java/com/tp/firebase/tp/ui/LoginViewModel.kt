package com.tp.firebase.tp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db : FirebaseFirestore
) : ViewModel() {

    val currentUser = auth.currentUser
    val docRef: DocumentReference? by lazy {
        currentUser?.let { db.collection("usuario").document(it.uid) }
    }

    val username = MutableStateFlow<String>("")

    init {
        getUsername()
    }


    fun getUsername(){

        viewModelScope.launch {

            val documentSnaptshot = docRef?.get()?.await()
            val name = documentSnaptshot?.getString("username") ?: ""

            username.value = name.toString()
        }
    }
}



