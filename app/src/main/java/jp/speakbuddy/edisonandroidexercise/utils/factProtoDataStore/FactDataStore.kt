package jp.speakbuddy.edisonandroidexercise.utils.factProtoDataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import jp.speakbuddy.edisonandroidexercise.FactPreference
import jp.speakbuddy.edisonandroidexercise.model.Fact
import jp.speakbuddy.edisonandroidexercise.utils.AppConstants.DATA_STORE_FILE_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FactDataStore(private val context: Context) {
    private val Context.factProtoDataStore: DataStore<FactPreference> by dataStore(
        fileName = DATA_STORE_FILE_NAME,
        serializer = FactSerializer
    )

    suspend fun saveFact(fact: String, length: Int) {
        context.factProtoDataStore.updateData { factData ->
            factData.toBuilder()
                .setFact(fact)
                .setLength(length)
                .build()

        }
    }

    val getFact: Flow<Fact> = context.factProtoDataStore.data
        .map {
            Fact(it.fact, it.length)
        }

    val factStr: Flow<String> = context.factProtoDataStore.data
        .map {
            it.fact
        }

    val length: Flow<Int> = context.factProtoDataStore.data
        .map {
            it.length
        }

}