package cn.kotlin.wanadroid.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import cn.kotlin.wanadroid.App.Companion.context
import cn.kotlin.wanadroid.utils.Constant.AppName
import java.io.*

/**
 *
 * FileName: Utils
 * Author: nanzong
 * Date: 2019/4/1 10:01 AM
 * Description:
 * History:
 *
 */

object Utils {

    fun logE(msg: Any) {
        Log.e(AppName, msg.toString())
    }

    fun toast(msg: Any) {
        Toast.makeText(context, msg.toString(), Toast.LENGTH_SHORT).show()
    }

    fun dp2px(dp: Int): Int {
        val density = context!!.resources.displayMetrics.density
        return (dp * density + .5f).toInt()
    }


    fun writeToCache(fileName: String, obj: Any) {
        var fos: FileOutputStream? = null
        var oos: ObjectOutputStream? = null
        try {
            fos = context!!.openFileOutput(fileName, Context.MODE_PRIVATE)
            oos = ObjectOutputStream(fos)
            oos.writeObject(obj)
        } catch (o: IOException) {

        } finally {
            fos!!.close()
            oos!!.close()
        }
    }

    fun restoreObject(fileName: String): Any? {
        var fis: FileInputStream? = null
        var ois: ObjectInputStream? = null
        var `object`: Any? = null
        try {
            fis = context!!.openFileInput(fileName)
            ois = ObjectInputStream(fis)
            `object` = ois.readObject()
            return `object`
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } finally {
            if (ois != null) {
                ois.close()
            }
            if (fis != null) {
                fis.close()
            }

        }
        return `object`
    }
}