package com.apiguave.newTins.domain.auth

import android.content.Intent
import com.apiguave.newTins.data.datasource.SignInCheck
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val isUserSignedIn: Boolean
    val userId: String
    suspend fun signInWithGoogle(data: Intent?, signInCheck: SignInCheck = SignInCheck.ALL_USERS): FirebaseUser
    fun signOut()
}