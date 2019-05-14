package com.brandontm.timechecker.entities

import com.google.gson.annotations.SerializedName

data class User(@SerializedName("id") var id: Int,
                @SerializedName("username") var username: String,
                @SerializedName("pass") var password: String,
                @SerializedName("birthday") var birthday: String)