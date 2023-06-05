package com.apiguave.newTins.data.repository

import com.apiguave.newTins.data.datasource.AuthRemoteDataSource
import com.apiguave.newTins.data.datasource.FirestoreRemoteDataSource
import com.apiguave.newTins.data.datasource.StorageRemoteDataSource
import com.apiguave.newTins.data.datasource.model.FirestoreMatch
import com.apiguave.newTins.domain.match.MatchRepository
import com.apiguave.newTins.domain.match.entity.Match
import com.apiguave.newTins.extensions.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class MatchRepositoryImpl(
    private val authDataSource: AuthRemoteDataSource,
    private val storageDataSource: StorageRemoteDataSource,
    private val firestoreDataSource: FirestoreRemoteDataSource
): MatchRepository {


    override suspend fun getMatches(): List<Match> {
        val matchModels = firestoreDataSource.getFirestoreMatchModels()
        val matches = coroutineScope {
            matchModels.map { async { it.toMatch() } }.awaitAll()
        }
        return matches.filterNotNull()
    }

    private suspend fun FirestoreMatch.toMatch(): Match? {
        val userId = this.usersMatched.firstOrNull { it != authDataSource.userId } ?: return null
        val user = firestoreDataSource.getFirestoreUserModel(userId)
        val picture = storageDataSource.getPictureFromUser(userId, user.pictures.first())
        return Match(
            this.id,
            user.birthDate?.toAge() ?: 99,
            userId,
            user.name,
            picture.uri,
            this.timestamp?.toShortString() ?: "",
            this.lastMessage
        )
    }
}