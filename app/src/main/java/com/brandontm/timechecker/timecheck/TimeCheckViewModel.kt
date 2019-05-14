package com.brandontm.timechecker.timecheck

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.brandontm.timechecker.services.WebService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class TimeCheckViewModel @Inject constructor() : ViewModel(), CoroutineScope {
    private val LOG_TAG: String = this::class.java.simpleName

    private val webService = WebService.create()
    private val timecheckRepository = TimecheckRepository(webService)
    var status: MutableLiveData<Int> = MutableLiveData()

    private val viewModelJob = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + viewModelJob

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun postTimecheck(userId: Int, actionId: Int): LiveData<Int> {
        launch {
            try {
                val result = timecheckRepository.postTimecheck(userId, actionId)
                result.await()?.let {
                    status.value = it.get("status").asInt
                }
            } catch (e: Exception) {
                Log.e(LOG_TAG, "Error posting timecheck.", e)
            }
        }

        return status
    }
}