package cn.kotlin.wanandroid.net

import cn.kotlin.wanandroid.bean.*
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
     * 首页Banner
     */
    @GET("/banner/json")
    fun getBanner(): Observable<BannerBean>

    /**
     * 首页数据
     * http://www.wanandroid.com/article/list/0/json
     * @param page page
     */
    @GET("/article/list/{page}/json")
    fun getHomeList(@Path("page") page: Int): Observable<HomeListBean>

    /**
     * 登录
     * @param  username
     * @param password
     */
    @POST("/user/login")
    fun loginWanAndroid(@Query("username") username: String,
                        @Query("password") password: String): Observable<LoginResponse>

    /**
     * 收藏文章
     * @param id id
     * @return Deferred<HomeListResponse>
     */
    @POST("/lg/collect/{id}/json")
    fun addCollectArticle(@Path("id") id: Int): Observable<HomeListBean>

    /**
     * 删除收藏文章
     * @param id id
     * @return Deferred<HomeListResponse>
     */
    @POST("/lg/uncollect_originId/{id}/json")
    fun removeCollectArticle(
            @Path("id") id: Int): Observable<HomeListBean>

    /**
     * 注册
     */
    @POST
    @FormUrlEncoded
    fun registerWanAndroid(
            @Field("username") username: String,
            @Field("password") password: String,
            @Field("repassword") repassword: String
    ): Observable<LoginResponse>

}