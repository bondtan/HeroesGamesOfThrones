package com.tbondarenko.heroesgamesofthrones.di

import com.tbondarenko.heroesgamesofthrones.data.remotedata.HeroesRemoteData
import com.tbondarenko.heroesgamesofthrones.data.remotedata.HeroesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesBaseUrl(): String = "https://thronesapi.com/"

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
                OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideHeroesService(retrofit: Retrofit): HeroesService =
        retrofit.create(HeroesService::class.java)

    @Provides
    @Singleton
    fun provideHeroesRemoteData(heroesService: HeroesService): HeroesRemoteData =
        HeroesRemoteData(heroesService)


}