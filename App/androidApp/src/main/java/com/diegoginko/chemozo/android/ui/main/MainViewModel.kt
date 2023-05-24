package com.diegoginko.chemozo.android.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class MainViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val brokerPressed = MutableLiveData<Boolean>()
    val clientPressed = MutableLiveData<Boolean>()

    fun onBroker(){
        brokerPressed.postValue(true)
    }


    fun onClient(){
        clientPressed.postValue(true)
    }
}