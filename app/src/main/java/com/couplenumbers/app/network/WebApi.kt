package com.couplenumbers.app.network

import com.couplenumbers.app.model.NumberModel
import retrofit2.Call
import retrofit2.http.GET

interface WebApi {
    // To get number.
    @GET("raw/8wJzytQX")
    fun getNumbers(): Call<NumberModel>
}