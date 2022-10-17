package com.hojibobo.hojibobopost.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Order : Serializable {


    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("payment")
    @Expose
    var payment: String? = null

    @SerializedName("user")
    @Expose
    var user: User? = null

    @SerializedName("products")
    @Expose
    var products: ArrayList<OrderedProduct>? = null

    @SerializedName("date_created")
    @Expose
    var date_created: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("order_number")
    @Expose
    var order_number: String? = null

    @SerializedName("address")
    @Expose
    var address: String? = null


}
