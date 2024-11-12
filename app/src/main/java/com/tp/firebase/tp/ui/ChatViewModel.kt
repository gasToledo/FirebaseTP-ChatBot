package com.tp.firebase.tp.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.tp.firebase.tp.BuildConfig
import com.tp.firebase.tp.core.constants.Constants
import com.tp.firebase.tp.domain.MessageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val db: FirebaseFirestore,
    private val analytics: FirebaseAnalytics,
    private val crashlytics: FirebaseCrashlytics,
    private val remoteConfig: FirebaseRemoteConfig
) : ViewModel() {

    private val _chatPersonality = MutableStateFlow(0)
    val chatPersonality: StateFlow<Int> = _chatPersonality.asStateFlow()

    private val _messageList = MutableStateFlow<List<MessageModel>>(emptyList())
    val messageList: StateFlow<List<MessageModel>> = _messageList.asStateFlow()

    private val _generativeModel = MutableStateFlow<GenerativeModel?>(null)
    val generativeModel: StateFlow<GenerativeModel?> = _generativeModel.asStateFlow()

    val parameters = Bundle().apply {
        this.putString("screen_name", "Chat")
        this.putString("chat", "ha comenzado un chat")
        this.putInt("personality_number", _chatPersonality.value)
    }

    fun logEvent() {
        analytics.setDefaultEventParameters(parameters)
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getPersonality()

            _generativeModel.value = GenerativeModel(
                modelName = "gemini-1.5-flash",
                apiKey = BuildConfig.API_KEY,
                systemInstruction = content {
                    text(checkPersonality(chatPersonality.value))
                }
            )
        }
    }

    fun sendMessage(question: String) {
        try {
            viewModelScope.launch {
                val chat = _generativeModel.value?.startChat(
                    history = _messageList.value.map {
                        content(it.rol) {
                            text(it.message.toString())
                        }
                    }.toList(),
                )

                logEvent()
                _messageList.update {
                    it + MessageModel(question, "user")
                }

                val response = chat?.sendMessage(question)
                _messageList.update {
                    it + MessageModel(response?.text.toString().trimEnd(), "model")
                }
            }
        } catch (
            e: Exception
        ) {
            _messageList.value =
                messageList.value + (MessageModel("Error al enviar el mensaje", "model"))

            crashlytics.recordException(e)
            crashlytics.setCustomKey("Chat", "Error al enviar el mensaje")
        }
    }

    suspend fun getPersonality() {
        try {
            remoteConfig.fetch(0)
            remoteConfig.activate().await()

            _chatPersonality.value = remoteConfig.getString("personalityNumber").toInt()

            analytics.logEvent("personality_fetched") {
                param("personality", _chatPersonality.value.toString())
            }
            Log.d("Personality", _chatPersonality.value.toString())

        } catch (e: Exception) {
            crashlytics.recordException(e)
            crashlytics.setCustomKey("personality", "Error al obtener la personalidad")
        }

    }

    private fun checkPersonality(number: Int): String {
            return when (number) {
                1 -> Constants.P1
                2 -> Constants.P2
                3 -> Constants.P3
                else -> Constants.P1
            }
    }

    fun clearMessages() {
        try {
            viewModelScope.launch {
                _messageList.value = emptyList()
            }
        } catch (e: Exception) {
            crashlytics.recordException(e)
            crashlytics.setCustomKey("Chat", "Error al limpiar los mensajes")
        }
    }
}

