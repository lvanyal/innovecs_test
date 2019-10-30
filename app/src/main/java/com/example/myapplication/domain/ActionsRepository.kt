package com.example.myapplication.domain

import io.reactivex.Single

interface ActionsRepository {
    fun getActions(): Single<List<ActionConfig>>
    fun getActionLastUsedTime(action: Action): Long?
    fun saveActionLastUsedTime(action: Action, time: Long)
}