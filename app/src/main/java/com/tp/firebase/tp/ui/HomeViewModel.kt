package com.tp.firebase.tp.ui

import android.util.Log
import android.util.Log.e
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig,
    private val auth: FirebaseAuth,
    private val db : FirebaseFirestore,
    private val crashlytics: FirebaseCrashlytics
): ViewModel(){

    val isChatActive = MutableStateFlow<Boolean>(false)

    private val _userUid = MutableStateFlow<String?>(" ")
    val userUid : StateFlow<String?> = _userUid.asStateFlow()

    private val _username = MutableStateFlow<String?>(null)
    val username : StateFlow<String?> = _username.asStateFlow()


    init {
        viewModelScope.launch {
            getIsChatActive()
            val user = auth.currentUser
            _userUid.value = user?.uid.toString()
            getUsername(_userUid.value.toString())
        }
    }

    fun runCrash(){
        try {
            throw Exception("Home Test Error")

        }catch (e: Exception) {
            e.printStackTrace()
            Log.e("Home", "test Exception ON")
            crashlytics.log("Home Test Error")
            crashlytics.setCustomKey("Home", "Home Test Error")
        }
    }

    suspend fun getIsChatActive() {

        remoteConfig.fetch(0)
        remoteConfig.activate().await()

        isChatActive.value = remoteConfig.getBoolean("isChatActive")
    }

    fun getUsername(uid: String) {

        try {

            val docRef = db.collection("usuario").document(uid)
            docRef.get().addOnSuccessListener { document ->
                if (document != null) {
                    _username.value = document.getString("username")
                }
            }

        }catch (e: Exception){
            e.printStackTrace()
            _username.value = "Usuario"
        }


    }

    fun clearUsername() {
        _username.value = null
    }
}