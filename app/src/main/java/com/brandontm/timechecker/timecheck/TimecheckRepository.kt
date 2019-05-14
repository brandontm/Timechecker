package com.brandontm.timechecker.timecheck

import com.brandontm.timechecker.services.WebService
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Response
import javax.inject.Inject

class TimecheckRepository @Inject constructor(private val webService: WebService) {
    fun postTimecheck(userId: Int, actionId: Int) =
            CoroutineScope(Dispatchers.IO).async {
                val jsonData = JsonObject()
                jsonData.addProperty("userId", userId)
                jsonData.addProperty("actionId", actionId)

                val response: Response<JsonObject> = this@TimecheckRepository.webService
                        .timecheck(jsonData).execute()

                response.body()
            }
}
