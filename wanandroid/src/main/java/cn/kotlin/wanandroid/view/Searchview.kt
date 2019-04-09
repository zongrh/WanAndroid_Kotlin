package cn.kotlin.wanandroid.view

import cn.kotlin.wanandroid.bean.HomeListBean

/**
 *
 * FileName: Searchview
 * Author: nanzong
 * Date: 2019/4/9 4:47 PM
 * Description:
 * History:
 *
 */
interface Searchview : iBaseView {

    fun searchResult(result: HomeListBean)

    fun collectSuccess(result:HomeListBean,position:Int)

    fun cancelCollectSuccess(result: HomeListBean, position: Int)

}