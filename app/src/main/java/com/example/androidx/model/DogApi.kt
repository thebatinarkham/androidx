package com.example.androidx.model

import io.reactivex.Single
import retrofit2.http.GET

interface DogApi {
    @GET("thebatinarkham/TodoApi/master/todoApi.json")
    fun getDogs():Single<List<DogBreed>>

}