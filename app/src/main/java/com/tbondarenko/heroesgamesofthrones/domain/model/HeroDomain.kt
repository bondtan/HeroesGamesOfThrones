package com.tbondarenko.heroesgamesofthrones.domain.model

data class HeroDomain (
    val id: Int,
    val firstName: String,
    val lastName: String,
    val fullName: String,
    val title: String,
    val family:String,
    val imageUrl: String
        )