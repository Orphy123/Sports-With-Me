package com.apiguave.newTins.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.newTins.domain.message.MessageRepository
import com.apiguave.newTins.domain.match.entity.Match
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(private val messageRepository: MessageRepository): ViewModel() {
    private val _match = MutableStateFlow<Match?>(null)
    val match = _match.asStateFlow()

    fun getMessages(matchId: String) = messageRepository.getMessages(matchId)

    fun sendMessage(text: String){
        val matchId = _match.value?.id ?: return
        viewModelScope.launch {
            try {
                messageRepository.sendMessage(matchId, text)
            }catch (e: Exception){
                //Delete the message from the displayed list
            }
        }
    }

    fun setMatch(match: Match){
        _match.value = match
    }
}
