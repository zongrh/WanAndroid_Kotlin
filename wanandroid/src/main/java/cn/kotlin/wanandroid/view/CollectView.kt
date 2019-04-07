package cn.kotlin.wanandroid.view

import cn.kotlin.wanandroid.bean.HomeListBean

/**
 *
 * FileName: CollectView
 * Author: nanzong
 * Date: 2019/4/2 2:26 PM
 * Description:
 * History:
 *
 */
interface CollectView : iBaseView {

    fun getCollectSuc(result: HomeListBean)

    fun collectSuccess(result: HomeListBean, position: Int)

    fun collectOutArticle(result: HomeListBean)

    fun cancelCollectSuccess(result: HomeListBean, position: Int)

}