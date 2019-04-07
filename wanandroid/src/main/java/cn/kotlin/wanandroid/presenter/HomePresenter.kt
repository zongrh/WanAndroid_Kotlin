package cn.kotlin.wanandroid.presenter

/**
 *
 * FileName: HomePresenter
 * Author: nanzong
 * Date: 2019/4/3 11:08 AM
 * Description:
 * History:
 *
 */
interface HomePresenter {

    fun getBanner()

    fun getHomeList(curPage: Int)

    fun collect(id: Int, position: Int)

    fun cancelCollect(id: Int, position: Int)

}