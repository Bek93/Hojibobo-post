package com.hojibobo.hojibobopost.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.hojibobo.hojibobopost.base.BaseModel

class User : BaseModel() {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("username")
    @Expose
    var username: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("address")
    @Expose
    var address: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("token")
    @Expose
    var token: String? = null

    @SerializedName("language")
    @Expose
    var language: String? = null

    fun isComplete(): Int {
        var isComplete = 1;
        if (name.isNullOrEmpty()) {
            isComplete = 0
        }

        if (phone.isNullOrEmpty()) {
            isComplete = 0
        }

        if (address.isNullOrEmpty() || image.isNullOrEmpty()) {
            isComplete = 0
        }
        return isComplete
    }
}
