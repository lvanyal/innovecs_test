package com.example.myapplication.domain

import io.reactivex.Single
import java.util.*

class GetActionUseCase(private val repository: ActionsRepository) {
    fun execute(): Single<Action> {
        return repository.getActions().map {
            getValidAction(it)
        }
    }

    private fun getValidAction(actions: List<ActionConfig>): Action {
        val validAction =
            actions.sortedByDescending { it.priority }.firstOrNull {
                isValid(it)
            }
        validAction?.let {
            repository.saveActionLastUsedTime(
                getAction(it.type),
                Calendar.getInstance().timeInMillis
            )
        }
        return getAction(validAction?.type)
    }

    private fun getAction(action: String?): Action {
        return when (action) {
            "animation" -> Action.ANIMATION
            "toast" -> Action.TOAST
            "call" -> Action.CALL
            "notification" -> Action.NOTIFICATION
            else -> Action.NONE
        }
    }

    private fun isValid(actionConfig: ActionConfig): Boolean {
        if (!actionConfig.enabled) return false

        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_WEEK) - 1
        if (!actionConfig.validDays.contains(currentDay)) return false

        val lastTimeActionUsed = repository.getActionLastUsedTime(getAction(actionConfig.type))
        if (lastTimeActionUsed != null) {
            val cooledDate = Calendar.getInstance().also {
                it.timeInMillis = lastTimeActionUsed
                it.add(Calendar.MILLISECOND, actionConfig.coolDown)
            }
            val isCooledDown = cooledDate.timeInMillis < calendar.timeInMillis
            if (!isCooledDown) {
                return false
            }
        }

        return true
    }
}