package com.hojibobo.hojibobopost

import android.content.Context
import com.google.gson.GsonBuilder
import com.hojibobo.hojibobopost.base.PreferencesManager
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Bek on 05/07/2017.
 */
object ApiService {
    private var mRetrofit: Retrofit? = null

    fun provideRetrofit(context: Context): Retrofit {
        if (mRetrofit == null) {
            val gson = GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .create()

            mRetrofit = Retrofit.Builder()
                .baseUrl("http://prod-api.hojibobo.com/api/")
                .client(provideOkHttpClient(context))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return mRetrofit!!
    }

    private fun provideOkHttpClient(context: Context): OkHttpClient {
        return getUnsafeOkHttpClient(context)
    }


    /**
     * OkHttpClient
     */
    private fun getUnsafeOkHttpClient(context: Context): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.HEADERS
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(SupportInterceptor(context))
            .authenticator(SupportInterceptor(context))
        return builder.build()
    }


    class SupportInterceptor(var context: Context) : Interceptor, Authenticator {
        override fun authenticate(route: Route?, response: Response): Request? {
            return response.request.newBuilder()
                .build()
        }

        /**
         * Interceptor class for setting of the headers for every request
         */
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            var user = PreferencesManager.getInstance(context).user
            if (user != null) {
                request = request?.newBuilder().addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer " + user!!.token)
                    .build()
            } else {
                request = request?.newBuilder().addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build()
            }
            return chain.proceed(request)
        }


    }

    fun <T> provideApi(service: Class<T>, context: Context): T {
        return provideRetrofit(context).create(service)
    }
}