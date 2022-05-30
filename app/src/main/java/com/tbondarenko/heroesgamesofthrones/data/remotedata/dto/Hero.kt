package com.tbondarenko.heroesgamesofthrones.data.remotedata.dto

import com.google.gson.annotations.SerializedName

data class Hero(
    @SerializedName("id") val id:Int,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("fullName")val fullName:String,
    @SerializedName("title") val title:String,
    @SerializedName("family") val family:String,
    @SerializedName("imageUrl")val imageUrl:String,
    )