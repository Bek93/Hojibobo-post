package com.hojibobo.hojibobopost.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class OrderedProduct : Serializable{
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("product")
    @Expose
    var product: Product? = null
    @SerializedName("quantity")
    @Expose
    var quantity: Int? = null
}