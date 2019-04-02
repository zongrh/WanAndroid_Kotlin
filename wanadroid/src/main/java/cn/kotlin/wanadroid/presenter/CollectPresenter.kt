package cn.kotlin.wanadroid.presenter

/**
 *
 * FileName: CollectPresenter
 * Author: nanzong
 * Date: 2019/4/2 2:35 PM
 * Description:
 * History:
 *
 */

interface CollectPresenter {

    fun getCollectList(curPage: Int)

    fun collect(id: Int, position: Int)

    fun cancelCollect(id: Int, position: Int)

    fun collectOutArticle(title:String,author:String,link:String)
}