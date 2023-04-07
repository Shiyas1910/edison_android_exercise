package jp.speakbuddy.edisonandroidexercise.datalayer.repository

import jp.speakbuddy.edisonandroidexercise.datautils.ApiDataConverter
import jp.speakbuddy.edisonandroidexercise.model.Fact
import jp.speakbuddy.edisonandroidexercise.network.Resource
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactApiService
import jp.speakbuddy.edisonandroidexercise.utils.factProtoDataStore.FactDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class FactRepositoryImpl @Inject constructor(
    private val factService: FactApiService,
    private val factDataStore: FactDataStore,
    private val apiDataConverter: ApiDataConverter
) : FactRepository {
    override suspend fun loadFacts(): Flow<Resource<Fact>> {
        factService.getFact().let { apiDataConverter.toFact(it) }
            .also { factDataStore.saveFact(it.fact, it.length) }
            .also { return getFacts() }
    }

    override suspend fun fetchAndSaveFacts() {
        factService.getFact().let { apiDataConverter.toFact(it) }.also {
            factDataStore.saveFact(it.fact, it.length)
        }
    }

    override fun getFacts(): Flow<Resource<Fact>> = flow {
        val fact = factDataStore.getFact.firstOrNull()
        emit(Resource.success(fact))
    }.flowOn(Dispatchers.IO)
}