package cn.kotlin.wanadroid.utils

import android.content.Context
import android.content.SharedPreferences
import java.lang.IllegalArgumentException
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 共享文件
 */
class Preference<T>(private val name: String, private val default: T) : ReadWriteProperty<Any?, T> {
    companion object {
        lateinit var preference: SharedPreferences
        //初始化application传入
        fun setContext(context: Context) {
            preference = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        }

        //清除数据
        fun clear() = preference.edit().clear().apply()
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = findPreference(name, default)

    //存储Key 为name  值为value
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = putPreference(name, default)


    //查找 对于name的数据
    private fun <T> findPreference(name: String, default: T): T = with(preference) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("This type can be get from Preferences")
        }
        res as T
    }

    private fun putPreference(name: String, value: T) = with(preference.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }.apply()

    }


}