package com.brandontm.timechecker.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.brandontm.timechecker.entities.User
import com.brandontm.timechecker.services.WebService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LoginViewModel @Inject constructor() : ViewModel(), CoroutineScope {
    private val LOG_TAG: String = this::class.java.simpleName

    private val webService = WebService.create()
    private val userRepository = UserRepository(webService)
    private val users = MutableLiveData<List<User>>()

    private val viewModelJob = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + viewModelJob

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun getUsers(): LiveData<List<User>> {
        launch {
            try {
                val result = userRepository.getUsers()
                result.await()?.let {
                    users.value = it
                }
            } catch (e: Exception) {
                Log.e(LOG_TAG, "Error loading users.", e)
            }
        }

        return users
    }
}