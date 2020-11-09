package com.example.androidx.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.androidx.model.DogBreed
import com.example.androidx.model.DogDatabase
import kotlinx.coroutines.launch

class DetailViewModel(application : Application) :BaseViewModel(application){
    val dogLiveData = MutableLiveData<DogBreed>()

    fun fetch(uuid:Int){
        launch{
            val dog =  DogDatabase(getApplication()).dogDao().getDog(uuid)
            dogLiveData.value = dog
        }


    }

}