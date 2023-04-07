package jp.speakbuddy.edisonandroidexercise.datautils

import jp.speakbuddy.edisonandroidexercise.model.Fact
import jp.speakbuddy.edisonandroidexercise.model.FactResponse

class ApiDataConverter {

    fun toFact(factResponse: FactResponse) = Fact(factResponse.fact, factResponse.length)

}