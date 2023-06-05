package com.apiguave.newTins.domain.profile

import com.apiguave.newTins.domain.profile.entity.CreateUserProfile
import com.apiguave.newTins.domain.profilecard.entity.CurrentProfile
import com.apiguave.newTins.domain.profile.entity.UserPicture

interface ProfileRepository {
    suspend fun createUserProfile(profile: CreateUserProfile)
    suspend fun createUserProfile(userId: String, profile: CreateUserProfile)
    suspend fun updateProfile(currentProfile: CurrentProfile, newBio: String, newGenderIndex: Int, newOrientationIndex: Int, newPictures: List<UserPicture>): CurrentProfile
}