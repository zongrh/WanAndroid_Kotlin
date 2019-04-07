package cn.kotlin.wanandroid.view

import cn.kotlin.wanandroid.bean.BannerBean
import cn.kotlin.wanandroid.bean.HomeListBean

/**
 *
 * FileName: HomeView
 * Author: nanzong
 * Date: 2019/4/3 10:33 AM
 * Description:
 * History:
 *
 */
interface HomeView : iBaseView {

    fun loadBannerSuccess(result: BannerBean)

    fun loadHomeListSuccess(result: HomeListBean)

    fun collectSuccess(result: HomeListBean, position: Int)

    fun cancelCollectSuccess(result: HomeListBean, position: Int)

}