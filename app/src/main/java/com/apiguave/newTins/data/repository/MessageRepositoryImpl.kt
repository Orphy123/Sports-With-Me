package com.apiguave.newTins.data.repository

import com.apiguave.newTins.data.datasource.FirestoreRemoteDataSource
import com.apiguave.newTins.domain.message.MessageRepository

class MessageRepositoryImpl(private val firestoreDataSource : FirestoreRemoteDataSource):
    MessageRepository {

    override fun getMessages(matchId: String) = firestoreDataSource.getMessages(matchId)

    override suspend fun sendMessage(matchId: String, text: String) {
        firestoreDataSource.sendMessage(matchId, text)
    }
}