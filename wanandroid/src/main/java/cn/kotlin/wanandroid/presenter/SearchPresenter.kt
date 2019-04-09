package cn.kotlin.wanandroid.presenter

/**
 *
 * FileName: SearchPresenter
 * Author: nanzong
 * Date: 2019/4/9 2:00 PM
 * Description:
 * History:
 *
 */
interface SearchPresenter {

    fun getSearch(page: Int, content: String)

    fun collect(id:Int,position:Int)

    fun cancelCollect(id: Int,position: Int)
}