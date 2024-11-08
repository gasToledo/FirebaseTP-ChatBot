package com.tp.firebase.tp.core

import com.google.firebase.auth.FirebaseAuth
import com.tp.firebase.tp.data.AuthRepository
import com.tp.firebase.tp.data.IAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesAuthRepository(
        firebaseAuth: FirebaseAuth,
    ): IAuthRepository {
        return AuthRepository(firebaseAuth)
    }

}