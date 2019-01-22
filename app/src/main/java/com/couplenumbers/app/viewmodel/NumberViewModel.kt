package com.couplenumbers.app.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.couplenumbers.app.model.NumberModel
import com.couplenumbers.app.network.getApiInstance
import com.couplenumbers.app.utils.COUPLE_NUMBER
import com.couplenumbers.app.utils.Resource
import com.couplenumbers.app.utils.Status
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NumberViewModel : ViewModel() {
    val numberLiveData = MutableLiveData<Resource<MutableList<NumberModel.Number>>>()

    fun callAPI() {
        numberLiveData.value = Resource(Status.LOADING)
        getApiInstance().getNumbers().enqueue(object : Callback<NumberModel> {
            override fun onFailure(call: Call<NumberModel>, t: Throwable) {
                t.printStackTrace()
                numberLiveData.value = Resource(Status.ERROR, t)
            }

            override fun onResponse(call: Call<NumberModel>, response: Response<NumberModel>) {
                if (response.isSuccessful) {
                    val numberModel = response.body()
                    val apiData = numberModel?.numbers
                    if (response.body() != null) {
                        findCoupleNumber(apiData)
                    } else {
                        numberLiveData.value = Resource(Status.ERROR)
                    }
                } else {
                    numberLiveData.value = Resource(Status.ERROR)
                }
            }
        })
    }

    private fun findCoupleNumber(numberList: MutableList<NumberModel.Number>?) {
        numberList ?: return
        for (parentIndex in 0 until numberList.size) {
            for (childIndex in 0 until numberList.size) {
                if (parentIndex != childIndex &&
                    numberList[parentIndex].number + numberList[childIndex].number == 0
                ) {
                    numberList[parentIndex].typeOfNumber = COUPLE_NUMBER
                    numberList[childIndex].typeOfNumber = COUPLE_NUMBER
                }
            }
        }
        numberLiveData.value = Resource(Status.SUCCESS, numberList)
    }
}