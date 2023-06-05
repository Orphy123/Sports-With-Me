package com.apiguave.newTins.domain.profilecard

import com.apiguave.newTins.domain.profilecard.entity.NewMatch
import com.apiguave.newTins.domain.profilecard.entity.Profile
import com.apiguave.newTins.domain.profilecard.entity.ProfileList

interface ProfileCardRepository {
    suspend fun swipeUser(profile: Profile, isLike: Boolean): NewMatch?
    suspend fun getProfiles(): ProfileList
}