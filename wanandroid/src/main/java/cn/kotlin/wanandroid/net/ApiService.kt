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
    fun removeCollectArticle(@Path("id") id: Int): Observable<HomeListBean>

    /**
     * 收藏站外文章
     */
    @POST("/lg/collect/add/json")
    @FormUrlEncoded
    fun addCollectOutsideArticle(
            @Field("title") title: String,
            @Field("author") author: String,
            @Field("link") link: String): Observable<HomeListBean>

    /**
     * 注册
     */
    @POST("/user/register")
    @FormUrlEncoded
    fun registerWanAndroid(
            @Field("username") username: String,
            @Field("password") password: String,
            @Field("repassword") repassowrd: String
    ): Observable<LoginResponse>

    /**
     * 获取我的收藏列表
     */
    @GET("/lg/collect/list/{page}/json")
    fun getCollectArticle(@Path("page") page: Int): Observable<HomeListBean>

    /**
     * 知识体系下的文章
     * http://www.wanandroid.com/article/list/0/json?cid=168
     * @param page page
     * @param cid cid
     */
    @GET("/article/list/{page}/json")
    fun getArticleList(
            @Path("page") page: Int,
            @Query("cid") cid: Int): Observable<HomeListBean>
    /**
     * 知识体系
     * http://www.wanandroid.com/tree/json
     */
    @GET("/tree/json")
    fun getTypeTreeList(): Observable<KnowledgeBean>
    /**
     * 搜索
     * http://www.wanandroid.com/article/query/0/json
     * @param page page
     * @param k POST search key
     */
    @POST("/article/query/{page}/json")
    @FormUrlEncoded
    fun getSearchList(
            @Path("page") page: Int,
            @Field("k") k: String): Observable<HomeListBean>
    /**
     * 大家都在搜
     * http://www.wanandroid.com/hotkey/json
     */
    @GET("/hotkey/json")
    fun getHotKeyList(): Observable<HotBean>

    /**
     * 常用网站
     * http://www.wanandroid.com/friend/json
     */
    @GET("/friend/json")
    fun getFriendList(): Observable<HotBean>
}