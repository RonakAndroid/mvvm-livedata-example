package com.couplenumbers.app.model

import com.couplenumbers.app.utils.NONE
import com.google.gson.annotations.SerializedName

data class NumberModel(
    @SerializedName("numbers")
    var numbers: ArrayList<Number>? = null
) {
    data class Number(
        @SerializedName("number")
        val number: Int,
        // To differentiate the couple number or other number type.
        var typeOfNumber: Int = NONE
    )
}