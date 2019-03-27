package cn.kotlin.wanadroid

import android.app.Application
import android.content.Context
import cn.kotlin.wanadroid.utils.Preference

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
//        初始化 SharePreference
        Preference.setContext(applicationContext)

    }

    companion object {
        var context: Context? = null
        fun getAppContext(): Context? {
            return context
        }
    }
}