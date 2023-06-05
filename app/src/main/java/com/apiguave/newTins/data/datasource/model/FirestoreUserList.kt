package com.apiguave.newTins.data.datasource.model

data class FirestoreUserList(
    val currentUser: FirestoreUser,
    val compatibleUsers: List<FirestoreUser>)