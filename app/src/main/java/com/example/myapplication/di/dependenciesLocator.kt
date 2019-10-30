package com.example.myapplication.di

import com.example.myapplication.data.ActonRepositoryImpl
import com.example.myapplication.domain.ActionsRepository
import com.example.myapplication.domain.GetActionUseCase

//todo rewrite to Koin
val getActionUseCase = GetActionUseCase(ActonRepositoryImpl())