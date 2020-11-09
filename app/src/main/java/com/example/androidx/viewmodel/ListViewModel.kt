package com.example.androidx.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.androidx.model.DogApiService
import com.example.androidx.model.DogBreed
import com.example.androidx.model.DogDatabase
import com.example.androidx.utils.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : BaseViewModel(application) {
    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()


    private val dogService = DogApiService()
    private val disposable = CompositeDisposable()
    private var prefHelper = SharedPreferencesHelper(getApplication())
    private var refreshTime = 5 * 60  * 1000 * 1000 * 10000L

    fun refresh() {
        val updateTime = prefHelper.getUpdateTime()
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            fetchFromDatabase()
        }
        else{
            fetchFromRemote()
        }

    }

    fun refreshBypassCache(){
        fetchFromRemote()
    }


    private fun fetchFromDatabase() {
        loading.value = true
        launch {
            val dogs = DogDatabase(getApplication()).dogDao().getAllDogs()
            dogRetrive(dogs)
            Toast.makeText(getApplication(),"Dogs Retrieved From Database.",Toast.LENGTH_LONG).show()
        }
    }


    private fun fetchFromRemote() {
        loading.value = true
        disposable.add(
            dogService.getDog().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<DogBreed>>() {
                    override fun onSuccess(dogList: List<DogBreed>) {
                        storeDogLocally(dogList)
                        Toast.makeText(getApplication(),"Dogs Retrieved From End Point.",Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        dogsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                })
        )

    }

    private fun dogRetrive(dogList:List<DogBreed>){
        dogs.value = dogList
        dogsLoadError.value = false
        loading.value = false
    }

    private fun storeDogLocally(list: List<DogBreed>){
        launch {
        val dao =  DogDatabase(getApplication()).dogDao()
            dao.deleteAllDogs()
            val result = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size){
                list[i].uuid = result[i].toInt()
                ++i
            }
            dogRetrive(list)

        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}

