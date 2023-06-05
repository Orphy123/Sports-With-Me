package com.apiguave.newTins.di

import com.apiguave.newTins.data.datasource.AuthRemoteDataSource
import com.apiguave.newTins.data.datasource.FirestoreRemoteDataSource
import com.apiguave.newTins.data.datasource.StorageRemoteDataSource
import com.apiguave.newTins.data.repository.*
import com.apiguave.newTins.domain.auth.AuthRepository
import com.apiguave.newTins.domain.match.MatchRepository
import com.apiguave.newTins.domain.message.MessageRepository
import com.apiguave.newTins.domain.profile.ProfileRepository
import com.apiguave.newTins.domain.profilecard.ProfileCardRepository
import com.apiguave.newTins.ui.chat.ChatViewModel
import com.apiguave.newTins.ui.editprofile.EditProfileViewModel
import com.apiguave.newTins.ui.home.HomeViewModel
import com.apiguave.newTins.ui.login.LoginViewModel
import com.apiguave.newTins.ui.matchlist.MatchListViewModel
import com.apiguave.newTins.ui.newmatch.NewMatchViewModel
import com.apiguave.newTins.ui.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {

    //Data sources
    single { AuthRemoteDataSource() }
    single { FirestoreRemoteDataSource() }
    single { StorageRemoteDataSource() }

    //Repositories
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<MatchRepository> { MatchRepositoryImpl(get(),get(),get()) }
    single<MessageRepository> { MessageRepositoryImpl(get()) }
    single<ProfileCardRepository> { ProfileCardRepositoryImpl(get(), get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get(),get(),get()) }
}