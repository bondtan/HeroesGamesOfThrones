package com.tbondarenko.heroesgamesofthrones.domain.repository

import com.tbondarenko.heroesgamesofthrones.data.remotedata.ApiResponse
import com.tbondarenko.heroesgamesofthrones.data.remotedata.HeroesRemoteData
import com.tbondarenko.heroesgamesofthrones.domain.MapperImpl
import com.tbondarenko.heroesgamesofthrones.domain.model.HeroDomain
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeroesRepository @Inject constructor(private val remoteData: HeroesRemoteData) {

    suspend fun getHeroesApi(): ApiResponse<List<HeroDomain>> {
        return try {
            val response = remoteData.getHeroes()
            val heroesDomainList = response.body()!!.map {
                MapperImpl.toDomainModel(it)
            }
            ApiResponse.Success(data = heroesDomainList)
        } catch (e: HttpException) {
            // handle exception with the request
            ApiResponse.Error(exception = e)
        } catch (e: IOException) {
            // handles no internet exception
            ApiResponse.Error(exception = e)
        }
    }
}
