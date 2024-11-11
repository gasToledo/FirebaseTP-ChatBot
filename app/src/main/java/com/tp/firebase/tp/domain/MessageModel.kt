package com.tp.firebase.tp.domain
import com.google.firebase.Timestamp

data class MessageModel(
    var message: String? = null,
    var rol: String? = null,
    var timestamp: Timestamp? = null
)