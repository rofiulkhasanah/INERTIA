package com.inertia.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SpinnerKeyValue(
    @SerializedName("key")
    @Expose
    var key: String? = null,

    @SerializedName("value")
    @Expose
    var value: String? = null,
) {
    override fun toString(): String {
        return value.toString()
    }
}

