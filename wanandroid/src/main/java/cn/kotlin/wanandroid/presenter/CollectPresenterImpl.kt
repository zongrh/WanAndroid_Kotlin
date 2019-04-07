package cn.kotlin.wanandroid.presenter

import cn.kotlin.wanandroid.view.CollectView

/**
 *
 * FileName: CollectPresenterImpl
 * Author: nanzong
 * Date: 2019/4/2 2:38 PM
 * Description:
 * History:
 *
 */
class CollectPresenterImpl(view: CollectView) : CollectPresenter {
    //获取收藏列表
    override fun getCollectList(curPage: Int) {

    }

    override fun collect(id: Int, position: Int) {
    }

    //取消收藏
    override fun cancelCollect(id: Int, position: Int) {
    }

    //收藏文章
    override fun collectOutArticle(title: String, author: String, link: String) {
    }

}