package com.hojibobo.hojibobopost.base

import com.hojibobo.hojibobopost.model.User


interface PreferencesHelper {


    val accessToken: String

    val fbToken: String

    var language: String?

    var isSignedIn: Int?

    var isComplete: Int?

    val isLanguageSelected: Int?

    var user: User?

    fun setAccessToken(accessToken: String)

    fun setSignedIn(signedIn: Int)

    fun setComplete(complete: Int)

    fun setLanguageSelected(languageSelected: Int)


}
