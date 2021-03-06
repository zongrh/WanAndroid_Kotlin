package cn.kotlin.wanandroid.presenter

import cn.kotlin.wanandroid.bean.HomeListBean
import cn.kotlin.wanandroid.net.ApiService
import cn.kotlin.wanandroid.net.RetrofitHelper
import cn.kotlin.wanandroid.view.CollectView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

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

    private var mView = view


    //获取收藏列表
    override fun getCollectList(curPage: Int) {
        mView.loading()
        RetrofitHelper.instance.create(ApiService::class.java)
                .getCollectArticle(curPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HomeListBean> {
                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(value: HomeListBean?) {
                        mView.getCollectSuc(value!!)
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
                    }

                    override fun onComplete() {
                        mView.loadComplete()
                    }

                })


    }

    override fun collect(id: Int, position: Int) {
    }

    //取消收藏
    override fun cancelCollect(id: Int, position: Int) {
        mView.loading()
        RetrofitHelper.instance.create(ApiService::class.java)
                .removeCollectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HomeListBean> {
                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(value: HomeListBean?) {
                        mView.cancelCollectSuccess(value!!, position)
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
                    }

                    override fun onComplete() {
                        mView.loadComplete()
                    }
                })
    }

    //收藏文章
    override fun collectOutArticle(title: String, author: String, link: String) {
        mView.loading()
        RetrofitHelper.instance.create(ApiService::class.java)
                .addCollectOutsideArticle(title, author, link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HomeListBean> {
                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(value: HomeListBean?) {
                        mView.collectOutArticle(value!!)
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
                    }

                    override fun onComplete() {
                        mView.loadComplete()
                    }

                })
    }

}