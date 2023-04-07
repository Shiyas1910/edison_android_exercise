package jp.speakbuddy.edisonandroidexercise.ui.fact

import jp.speakbuddy.edisonandroidexercise.model.FactResponse
import retrofit2.http.GET

interface FactApiService {
    @GET("fact")
    suspend fun getFact(): FactResponse
}