package com.tbondarenko.heroesgamesofthrones.data.remotedata

import com.tbondarenko.heroesgamesofthrones.data.remotedata.dto.Hero
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * A retrofit service to fetch a heroes list.
 */
interface HeroesService {

    @GET("api/v2/Characters/{id}")
    suspend fun fetchHero(@Path("id") heroId: Int): Response<Hero>

    @GET("api/v2/Characters")
    suspend fun fetchHeroes():Response<List<Hero>>
}