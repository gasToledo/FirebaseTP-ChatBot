package com.tp.firebase.tp.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private val crashlytics: FirebaseCrashlytics,
    private val analytics: FirebaseAnalytics
): ViewModel(){

    val isChatActive = MutableStateFlow<Boolean>(false)

    private val _userUid = MutableStateFlow<String?>("")
    val userUid : StateFlow<String?> = _userUid.asStateFlow()

    private val _username = MutableStateFlow<String?>(null)
    val username : StateFlow<String?> = _username.asStateFlow()



    val parameters = Bundle().apply {
        this.putString("screen_name", "Home")
    }
    fun logEvent(){
        analytics.logEvent("screen_name", parameters)
    }


    init {
        viewModelScope.launch(Dispatchers.IO) {
            getIsChatActive()
            getUsername()
        }
    }

    fun runCrash(){
        try {
            throw Exception("Home Test Error")

        }catch (e: Exception) {
            crashlytics.setCustomKey("Home", "CRASH DE PRUEBA")
            crashlytics.recordException(e)
        }
    }

    suspend fun getIsChatActive() {

        remoteConfig.fetch(0)
        remoteConfig.activate().await()

        isChatActive.value = remoteConfig.getBoolean("isChatActive")
    }

    fun getUsername() {

        _userUid.value = auth.currentUser?.uid

        try {
            db.collection("user").whereEqualTo("userId", _userUid.value).get()
                .addOnSuccessListener {
                    for (document in it) {
                        _username.value = document.getString("username")
                    }
            }
        }catch (e: Exception){
            e.printStackTrace()
            _username.value = "Usuario"
            crashlytics.setCustomKey("Home", e.printStackTrace().toString())
        }
    }

    fun clearUsername() {
        _username.value = ""
    }
}