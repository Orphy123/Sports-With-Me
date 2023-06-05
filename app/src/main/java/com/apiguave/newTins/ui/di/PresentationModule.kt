package com.apiguave.newTins.ui.di

import com.apiguave.newTins.ui.chat.ChatViewModel
import com.apiguave.newTins.ui.editprofile.EditProfileViewModel
import com.apiguave.newTins.ui.home.HomeViewModel
import com.apiguave.newTins.ui.login.LoginViewModel
import com.apiguave.newTins.ui.matchlist.MatchListViewModel
import com.apiguave.newTins.ui.newmatch.NewMatchViewModel
import com.apiguave.newTins.ui.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    //View models
    viewModel { ChatViewModel(get()) }
    viewModel { NewMatchViewModel(get()) }
    viewModel { EditProfileViewModel(get(), get()) }
    viewModel { SignUpViewModel(get(), get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { MatchListViewModel(get()) }
}