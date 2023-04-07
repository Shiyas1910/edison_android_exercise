package jp.speakbuddy.edisonandroidexercise.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.edisonandroidexercise.datalayer.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.datalayer.repository.FactRepositoryImpl
import jp.speakbuddy.edisonandroidexercise.datalayer.rest.RestApiService
import jp.speakbuddy.edisonandroidexercise.datautils.ApiDataConverter
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactApiService
import jp.speakbuddy.edisonandroidexercise.utils.factProtoDataStore.FactDataStore
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit = RestApiService.getRetrofitInstance()

    @Singleton
    @Provides
    fun provideFactApiService(retrofit: Retrofit): FactApiService =
        RestApiService.create(retrofit, FactApiService::class.java)

    @Singleton
    @Provides
    fun provideApiDataConverter(): ApiDataConverter = ApiDataConverter()

    @Singleton
    @Provides
    fun provideFactRepository(factRepository: FactRepositoryImpl): FactRepository = factRepository

    @Singleton
    @Provides
    fun provideFactDataStore(@ApplicationContext context: Context): FactDataStore =
        FactDataStore(context)
}