package com.apiguave.newTins.domain.message

import com.apiguave.newTins.domain.message.entity.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun getMessages(matchId: String): Flow<List<Message>>
    suspend fun sendMessage(matchId: String, text: String)
}