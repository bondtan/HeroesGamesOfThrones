package com.tbondarenko.heroesgamesofthrones.data.remotedata

import javax.inject.Inject

class HeroesRemoteData @Inject constructor(private val heroesService: HeroesService) {

    suspend fun getHero(heroId: Int)  = heroesService.fetchHero(heroId)

    suspend fun getHeroes() = heroesService.fetchHeroes()

}