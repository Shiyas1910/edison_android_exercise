package jp.speakbuddy.edisonandroidexercise.utils.factProtoDataStore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import jp.speakbuddy.edisonandroidexercise.FactPreference
import java.io.InputStream
import java.io.OutputStream

object FactSerializer: Serializer<FactPreference> {
    override val defaultValue: FactPreference = FactPreference.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): FactPreference {
        try {
            return FactPreference.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: FactPreference, output: OutputStream) {
        t.writeTo(output)
    }

}