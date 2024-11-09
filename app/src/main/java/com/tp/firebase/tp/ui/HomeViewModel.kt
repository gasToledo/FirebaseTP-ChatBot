package com.tp.firebase.tp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig
): ViewModel(){

    val isChatActive = MutableStateFlow<Boolean>(false)

    init {
        viewModelScope.launch {
            getIsChatActive()
        }
    }

    suspend fun getIsChatActive() {

        remoteConfig.fetch(0)
        remoteConfig.activate().await()

        isChatActive.value = remoteConfig.getBoolean("isChatActive")
    }
}