package jp.speakbuddy.edisonandroidexercise.datalayer.repository

import jp.speakbuddy.edisonandroidexercise.model.Fact
import jp.speakbuddy.edisonandroidexercise.network.Resource
import kotlinx.coroutines.flow.Flow

interface FactRepository {

    suspend fun loadFacts(): Flow<Resource<Fact>>

    suspend fun fetchAndSaveFacts()

    fun getFacts(): Flow<Resource<Fact>>
}