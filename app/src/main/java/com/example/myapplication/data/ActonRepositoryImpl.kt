package com.example.myapplication.data

import com.example.myapplication.data.retrofit.api
import com.example.myapplication.domain.Action
import com.example.myapplication.domain.ActionConfig
import com.example.myapplication.domain.ActionsRepository
import io.reactivex.Single

class ActonRepositoryImpl : ActionsRepository {

    private val actionUsedTimeCache = mutableMapOf<Action, Long>()

    override fun getActions(): Single<List<ActionConfig>> {
        return api.getActions().map {
            it.map { ActionConfig(it.cool_down, it.enabled, it.priority, it.type, it.valid_days) }
        }
    }

    override fun getActionLastUsedTime(action: Action): Long? {
        return actionUsedTimeCache[action]
    }

    override fun saveActionLastUsedTime(action: Action, time: Long) {
        actionUsedTimeCache[action] = time
    }

}