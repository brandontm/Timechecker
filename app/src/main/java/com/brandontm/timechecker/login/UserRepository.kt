package com.brandontm.timechecker.login

import com.brandontm.timechecker.entities.User
import com.brandontm.timechecker.services.WebService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val webService: WebService) {
    fun refreshUsers() {

    }

    fun getUsers() =
        CoroutineScope(Dispatchers.IO).async {
            val response: Response<List<User>> = this@UserRepository.webService.getUsers().execute()

            response.body()
        }
}
