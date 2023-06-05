package com.apiguave.newTins.extensions

import android.net.Uri
import com.apiguave.newTins.domain.profilecard.entity.CurrentProfile
import com.apiguave.newTins.data.datasource.model.FirestoreUser
import com.apiguave.newTins.domain.profilecard.entity.Profile
import com.apiguave.newTins.domain.profile.entity.FirebasePicture

fun FirestoreUser.toProfile(uris: List<Uri>): Profile {
    return Profile(this.id, this.name, this.birthDate?.toAge() ?: 99, uris)
}

fun FirestoreUser.toCurrentProfile(uris: List<FirebasePicture>): CurrentProfile {
    return CurrentProfile(
        this.id,
        this.name,
        this.birthDate?.toLongString() ?: "",
        this.bio,
        this.male?.let { if(it) 0 else 1 } ?: -1,
        this.orientation?.ordinal ?: -1,
        uris
    )
}