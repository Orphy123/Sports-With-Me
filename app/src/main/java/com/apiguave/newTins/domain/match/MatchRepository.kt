package com.apiguave.newTins.domain.match

import com.apiguave.newTins.domain.match.entity.Match

interface MatchRepository {
    suspend fun getMatches(): List<Match>
}