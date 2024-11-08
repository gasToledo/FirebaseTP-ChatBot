package com.tp.firebase.tp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.firebase.firestore.FirebaseFirestore
import com.tp.firebase.tp.BuildConfig
import com.tp.firebase.tp.domain.MessageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatScreenViewModel @Inject constructor(
    db: FirebaseFirestore
) : ViewModel() {

    private val _messageList = MutableStateFlow<List<MessageModel>>(emptyList())
    val messageList: StateFlow<List<MessageModel>> = _messageList.asStateFlow()

    val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.API_KEY,
    )

    fun sendMessage(question: String) {
        try {
            viewModelScope.launch {
                val chat = generativeModel.startChat(
                    history = _messageList.value.map {
                        content(it.rol) {
                            text(it.message.toString())
                        }
                    }.toList(),
                )

                _messageList.update {
                    it + MessageModel(question, "user")
                }

                val response = chat.sendMessage(question)
                _messageList.update {
                    it + MessageModel(response.text.toString().trimEnd(), "model")
                }
            }
        } catch (
            e: Exception
        ) {
            _messageList.value =
                messageList.value + (MessageModel("Error al enviar el mensaje", "model"))
        }
    }

    fun clearMessages() {
        _messageList.value = emptyList()
    }

}

