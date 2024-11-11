package com.tp.firebase.tp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.firebase.firestore.FieldValue
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
    private val db: FirebaseFirestore
) : ViewModel() {

    private val _messageList = MutableStateFlow<List<MessageModel>>(emptyList())
    val messageList: StateFlow<List<MessageModel>> = _messageList.asStateFlow()

    val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.API_KEY,
    )

    init {
        loadMessagesFromFirestore()
    }

    // Enviar mensaje y guardar en Firestore
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

                // Agregar el mensaje del usuario
                val userMessage = MessageModel(question, "user")
                _messageList.update { it + userMessage }
                saveMessageToFirestore(userMessage)


                // Obtener la respuesta de la IA y guardar
                val response = chat.sendMessage(question)
                val modelMessage = MessageModel(response.text.toString().trimEnd(), "model")
                _messageList.update { it + modelMessage }
                saveMessageToFirestore(modelMessage)
            }
        }
        catch ( e: Exception ) {
            _messageList.value = messageList.value + (MessageModel("Error al enviar el mensaje", "model"))
        }
    }


    // Guardar mensaje en Firestore
    private fun saveMessageToFirestore(message: MessageModel) {
        val messageData = hashMapOf(
            "message" to message.message,
            "role" to message.rol,
            "timestamp" to FieldValue.serverTimestamp()
        )

        db.collection("chats")
            .add(messageData)
            .addOnFailureListener {
                _messageList.value = messageList.value + (MessageModel("Error al guardar el mensaje", "model"))
            }
    }


    // Cargar mensajes guardados en Firestore
    private fun loadMessagesFromFirestore() {
        db.collection("chats")
            .orderBy("timestamp") // Ordenar cronológicamente
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    _messageList.value = messageList.value + (MessageModel("Error al cargar los mensajes", "model"))
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val messages = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(MessageModel::class.java)
                    }
                    _messageList.value = messages
                }
            }
    }


    fun clearMessages() {
        _messageList.value = emptyList()
    }

}