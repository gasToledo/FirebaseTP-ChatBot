package com.tp.firebase.tp.data

import com.tp.firebase.tp.domain.User

interface IAuthRepository {

    suspend fun signUpWithEmailAndPassword(email: String, password: String): Result<Unit>

    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<Unit>

    suspend fun signOut(): Result<Unit>

    fun getCurrentUser(): User?
}