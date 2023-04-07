package jp.speakbuddy.edisonandroidexercise.ui.fact

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.speakbuddy.edisonandroidexercise.datalayer.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.model.Fact
import jp.speakbuddy.edisonandroidexercise.network.Resource
import jp.speakbuddy.edisonandroidexercise.network.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactViewModel @Inject constructor(private val factRepository: FactRepository) : ViewModel() {

    private val _viewState = MutableStateFlow(
        Resource(
            Status.LOADING,
            Fact("", 0),
            ""
        )
    )
    val viewState = _viewState.asStateFlow()

    init {
        updateFact {
            Log.e("ViewModel", "Initiated")
        }
    }

    fun updateFact(completion: () -> Unit) {
        viewModelScope.launch {
            _viewState.value = Resource.loading()
            try {
                factRepository.loadFacts()
                    .catch {
                        _viewState.value =
                            Resource.error("Something went wrong. Error = ${it.message}")
                    }
                    .collect {
                        Log.e("ViewModel - Fact", it.data?.fact ?: "No Facts")
                        Log.e("ViewModel - Length", it.data?.length.toString())
                        _viewState.value = it
                    }.also { completion() }
            } catch (e: Exception) {
                _viewState.value = Resource.error("Something went wrong. Error = ${e.message}")
                Log.e("ViewModel - Exception", e.message.toString())
            }
        }
    }
}
