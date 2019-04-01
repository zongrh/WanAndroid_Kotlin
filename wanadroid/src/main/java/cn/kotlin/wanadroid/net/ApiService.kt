package cn.kotlin.wanadroid.net

import cn.kotlin.wanadroid.bean.*
import io.reactivex.Observable
import retrofit2.http.*

/*
 * @Author nanzong
 * @Date $time$ $date$
 * @Param $param$
 * @Description
 * @return $return$
 **/
interface ApiService {

    /**
     * 登录
     * @param  username
     * @param password
     */
    @POST("/user/login")
    fun loginWanAndroid(@Query("username") username: String,
                        @Query("password") password: String): Observable<LoginResponse>

    @POST
    @FormUrlEncoded
    fun registerWanAndroid(
            @Field("username") username: String,
            @Field("password") password: String,
            @Field("repassword") repassword: String
    ): Observable<LoginResponse>

}