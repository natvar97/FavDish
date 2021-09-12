package com.example.favdish.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.favdish.model.entities.RandomDish
import com.example.favdish.model.entities.network.RandomDishRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class RandomDishViewModel @Inject constructor(
    private val randomDishRepository: RandomDishRepository
) : ViewModel() {

    val loadRandomDish = MutableLiveData<Boolean>()
    val randomDishResponse = MutableLiveData<RandomDish.Recipes>()
    val randomDishLoadingError = MutableLiveData<Boolean>()

    fun getRandomRecipeFromAPI() {
        viewModelScope.launch {
            loadRandomDish.value = true
            try {
                coroutineScope {
                    val response = async {
                        randomDishRepository.getRandomDish()
                    }
                    randomDishResponse.postValue(response.await())
                }
            } catch (e: Exception) {
                loadRandomDish.value = true
                randomDishLoadingError.value = true
                e.printStackTrace()
            }
        }

    }

}