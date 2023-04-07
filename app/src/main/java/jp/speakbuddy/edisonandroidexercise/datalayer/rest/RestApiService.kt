package jp.speakbuddy.edisonandroidexercise.datalayer.rest

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import jp.speakbuddy.edisonandroidexercise.utils.AppConstants
import jp.speakbuddy.edisonandroidexercise.utils.AppConstants.BASE_URL
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object RestApiService {

    fun getRetrofitInstance(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory(AppConstants.CONTENT_TYPE.toMediaType()))
            .build()

    fun <T> create(retrofit: Retrofit, service: Class<T>): T {
        return retrofit.create(service)
    }
}