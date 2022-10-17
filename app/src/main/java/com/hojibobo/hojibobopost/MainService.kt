package com.hojibobo.hojibobopost

import com.google.gson.JsonElement
import com.hojibobo.hojibobopost.model.Order
import com.hojibobo.hojibobopost.model.User
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*


/**
 * Main Tab page Api
 */
interface MainService {


    @POST("mobile/users/")
    fun signUp(@Body user: User): Single<User>

    @POST("admin/auth-token/")
    fun signIn(@Body user: User): Single<User>

    @GET("admin/order/accepted_order_by_date/")
    fun getTodayAcceptOrderList(
        @Query("posting_date") date: String
    ): Single<ArrayList<Order>>

    @POST("admin/order/{id}/send_and_notify")
    fun sendAndNotify(@Path("id") id: Int, @Query("post_number") post_code: String): Completable

}
