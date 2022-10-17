package com.hojibobo.hojibobopost.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class Product : Serializable {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("label")
    @Expose
    var label: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("price")
    @Expose
    var price: Int? = null
    @SerializedName("discount")
    @Expose
    var discount: Int? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("unit")
    @Expose
    var unit: String? = null
    @SerializedName("date_created")
    @Expose
    var dateCreated: String? = null
    @SerializedName("date_modifed")
    @Expose
    var dateModifed: String? = null


}
