package com.hojibobo.hojibobopost.base

import android.content.Context
import android.content.SharedPreferences

import androidx.annotation.VisibleForTesting

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hojibobo.hojibobopost.BuildConfig
import com.hojibobo.hojibobopost.model.User

import javax.inject.Inject


/**
 * Created by Bek on 24/10/2019.
 */

class PreferencesManager @Inject
constructor(context: Context) : PreferencesHelper {
    override val accessToken: String
        get() = getValue(String::class.java, Key.signed, false) as String


    override val fbToken: String
        get() = getValue(String::class.java, Key.fbToken, "") as String
    override var language: String? = null
        get() = getValue(String::class.java, Key.language, "") as String
        set(value) {
            field = value!!
            setValue(Key.language, value)
        }
    override var isSignedIn: Int? = null
        get() = getValue(Int::class.java, Key.signed, 0) as Int
    override var isComplete: Int? = null
        get() = getValue(Int::class.java, Key.completed, 0) as Int
    override val isLanguageSelected: Int?
        get() = getValue(Int::class.java, Key.languageSelected, 0) as Int
    override var user: User? = null
        get() = getValue(User::class.java, Key.user) as User
        set(value) {
            field = value!!
            setValue(value, Key.user)
        }
    private val mPref: SharedPreferences


    override fun setAccessToken(accessToken: String) {

    }


    override fun setSignedIn(signedIn: Int) {
        setValue(Key.signed, signedIn)
    }

    override fun setComplete(complete: Int) {
        setValue(Key.completed, complete)
    }

    override fun setLanguageSelected(languageSelected: Int) {
        setValue(Key.languageSelected, languageSelected)
    }


    enum class Key {
        signed, languageSelected, completed, user, fbToken, language
    }

    init {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }


    fun setValue(value: BaseModel, key: Key) {
        setValue(key, Gson().toJson(value))
    }

    fun <T> getValue(aClass: Class<T>, key: Key): Any {
        return Gson().fromJson(getValue(String::class.java, key, "") as String?, aClass)!!
    }


    fun setValue(key: Key, value: Any) {
        if (value.javaClass == String::class.java) {
            mPref.edit().putString(key.name, value as String).apply()
        } else if (value.javaClass == Integer::class.java) {
            mPref.edit().putInt(key.name, value as Int).apply()
        } else if (value.javaClass == Float::class.java) {
            mPref.edit().putFloat(key.name, value as Float).apply()
        } else if (value.javaClass == Boolean::class.java) {
            mPref.edit().putBoolean(key.name, value as Boolean).apply()
        } else if (value.javaClass == Long::class.java) {
            mPref.edit().putLong(key.name, value as Long).apply()
        } else if (value.javaClass == object : TypeToken<Set<String>>() {

            }.javaClass) {
            mPref.edit().putStringSet(key.name, value as Set<String>).apply()
        } else {
            assert(true)
        }
    }

    fun <T> getValue(aClass: Class<T>, key: Key, defaultValue: Any): Any? {

        if (aClass == String::class.java) {
            return mPref.getString(key.name, defaultValue as String)
        } else if (aClass == Int::class.java) {
            return mPref.getInt(key.name, defaultValue as Int)
        } else if (aClass == Float::class.java) {
            return mPref.getFloat(key.name, defaultValue as Float)
        } else if (aClass == Boolean::class.java) {
            return mPref.getBoolean(key.name, defaultValue as Boolean)
        } else if (aClass == Long::class.java) {
            return mPref.getLong(key.name, defaultValue as Long)
        } else if (aClass == object : TypeToken<Set<String>>() {

            }.javaClass) {
            return mPref.getStringSet(key.name, defaultValue as Set<String>)
        } else {
            assert(true)
            return null
        }
    }

    fun remove(key: Key) {
        mPref.edit().remove(key.name).apply()
    }

    fun clear(): Boolean {
        return mPref.edit()
            .clear()
            .commit()
    }

    companion object {

        @VisibleForTesting
        val PREF_NAME = BuildConfig.APPLICATION_ID + ".local"

        private var sInstance: PreferencesManager? = null


        @Synchronized
        fun getInstance(context: Context): PreferencesManager {
            if (sInstance == null) {
                sInstance = PreferencesManager(context)
            }
            return sInstance!!
        }
    }

}
