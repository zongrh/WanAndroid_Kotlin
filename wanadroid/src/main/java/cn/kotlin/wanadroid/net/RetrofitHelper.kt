package cn.kotlin.wanadroid.net

import android.util.Log
import cn.kotlin.wanadroid.utils.Constant
import cn.kotlin.wanadroid.utils.Preference
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/*
 * @Author nanzong
 * @Date $time$ $date$
 * @Param $param$
 * @Description
 * @return $return$
 **/
class RetrofitHelper private constructor() {
    private val retrofit: Retrofit

    companion object {
        val instance by lazy { RetrofitHelper() }
    }

    init {
        retrofit = Retrofit.Builder()
                .baseUrl(Constant.REQUEST_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(initOkHttpClient())
                .build()

    }

    //初始化 OkHttp
    private fun initOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(initCookieIntercept())
                .addInterceptor(initLoginIntercept())
                .addInterceptor(initCommonIntercept())
                .addInterceptor(initLogInterceptor())
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build()
    }

    //设置请求cookie 自动登录
    private fun initCommonIntercept(): Interceptor {
        return Interceptor {
            val request = it.request()
            val builder = request.newBuilder()
            val domain = request.url().host()
            if (domain.isNotEmpty()) {
                val cookie: String by Preference(domain, "")
                if (cookie.isNotEmpty()) {
                    builder.addHeader(Constant.COOKIE_NAME, cookie)
                    Log.e("add :=",cookie)
                }
            }
            it.proceed(builder.build())
        }
    }

    //初始化日志
    private fun initLogInterceptor(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    //设置 请求cookie （自动登录）
    private fun initLoginIntercept(): Interceptor {
        return Interceptor {
            val request = it.request()
            val builder = request.newBuilder()
            val domain = request.url().host()
            if (domain.isNotEmpty()) {
                val cookie: String by Preference(domain, "")
                if (cookie.isNotEmpty()) {
                    builder.addHeader(Constant.COOKIE_NAME, cookie)
                    Log.e("add :=",cookie)
                }

            }
            it.proceed(builder.build())
        }
    }

    //cookie拦截器 获取 cookie （自动登录时候需要用到）
    private fun initCookieIntercept(): Interceptor {
        return Interceptor { chain ->
            //获取请求
            val request = chain.request()
            val response = chain.proceed(request)
            val requestUrl = request.url().toString()
            //获取 域名 （wanandroid.com）
            val domain = request.url().host()

            //如果 是（登录请求 或者注册请求）并且请求头包含 cookit
            if ((requestUrl.contains(Constant.SAVE_USER_LOGIN_KEY)
                            || requestUrl.contains(Constant.SAVE_USER_REGISTER_KEY))) {
                //获取 全部 cookie
                val cookie = response.headers(Constant.SET_COOKIE_KEY)

                cookie?.let {
                    //解析 cookie
                    val cookie = encodeCookit(it)
                    saveCookie(domain, cookie)
                }
            }
            response

        }
    }

    fun <T> create(clz: Class<T>): T {
        checkNotNull(clz)
        checkNotNull(retrofit)
        return retrofit.create(clz)
    }

    //解析 cookie
    private fun encodeCookit(cookies: List<String>): String {
        val sb = StringBuilder()
        //组装 cookie
        cookies.forEach { cookies ->
            sb.append(cookies).append(";")
        }
        //去掉 最后的 ";"
        return sb.deleteCharAt(sb.length - 1).toString()
    }

    //持久化存储
    private fun saveCookie(domain: String?, cookie: String) {
        //根据 domain 保存 cookie
        domain?.let {
            var spDomain: String by Preference(it, cookie)
            Log.e("save domain:=", domain)
            Log.e("save cookie:=", cookie)
            spDomain = cookie
        }
    }


}

















