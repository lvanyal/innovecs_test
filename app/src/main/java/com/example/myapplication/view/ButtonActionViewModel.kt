package com.example.myapplication.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.di.getActionUseCase
import com.example.myapplication.domain.Action
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers

class ButtonActionViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val currentAction = MutableLiveData<Action>(Action.NONE)

    private val useCase = getActionUseCase

    fun onActionButtonClick() {
        compositeDisposable += useCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Action>() {
                override fun onSuccess(action: Action) {
                    currentAction.postValue(action)
                }

                override fun onError(e: Throwable) {
                    //todo
                }
            })
    }

    override fun onCleared() {
        super.onCleared()
        this.compositeDisposable.clear()
    }
}
