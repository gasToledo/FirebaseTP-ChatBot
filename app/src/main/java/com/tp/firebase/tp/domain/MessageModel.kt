package com.tp.firebase.tp.domain

data class MessageModel(
    var message: String? = null,
    var rol: String? = null,
    var timestamp: Long = System.currentTimeMillis()
)