package com.tbondarenko.heroesgamesofthrones.ui.listfragment

import androidx.lifecycle.*
import com.tbondarenko.heroesgamesofthrones.data.remotedata.ApiResponse
import com.tbondarenko.heroesgamesofthrones.domain.model.HeroDomain
import com.tbondarenko.heroesgamesofthrones.domain.repository.HeroesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class HeroesListViewModel @Inject constructor(private val repository: HeroesRepository) :
    ViewModel() {

    private val _heroesList = MutableLiveData<List<HeroDomain>>(emptyList())
    val heroesList: LiveData<List<HeroDomain>>
        get() = _heroesList

    private val _errorMassage = MutableLiveData<String>()
    val errorMassage: LiveData<String>
        get() = _errorMassage

    private val _loading = MutableLiveData<Boolean>(true)
    val loading: LiveData<Boolean>
        get() = _loading

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handler: ${throwable.localizedMessage}")
    }

    init {
        getHeroes()
    }

    fun refreshHeroes() {
        getHeroes()
    }

    private fun getHeroes() = viewModelScope
        .launch(Dispatchers.IO + exceptionHandler) {
            val response = repository.getHeroesApi()
            withContext(Dispatchers.Main) {
                when (response) {
                    is ApiResponse.Success -> {
                        _heroesList.postValue(response.data!!)
                        _loading.value = false
                    }
                    is ApiResponse.Error -> {
                        onError("Error: ${response.exception}")
                    }
                }
            }
        }

    private fun onError(message: String) {
        _errorMassage.value = message
        _loading.value = false
    }
}



