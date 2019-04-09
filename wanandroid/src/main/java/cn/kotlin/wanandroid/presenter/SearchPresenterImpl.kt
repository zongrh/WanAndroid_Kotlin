package cn.kotlin.wanandroid.presenter

import cn.kotlin.wanandroid.bean.HomeListBean
import cn.kotlin.wanandroid.net.ApiService
import cn.kotlin.wanandroid.net.RetrofitHelper
import cn.kotlin.wanandroid.view.Searchview
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 *
 * FileName: SearchPresenterImpl
 * Author: nanzong
 * Date: 2019/4/9 2:15 PM
 * Description:
 * History:
 *
 */
class SearchPresenterImpl(view: Searchview) : SearchPresenter {
    private var mView: Searchview = view

    override fun getSearch(page: Int, content: String) {
        mView.loading()
        RetrofitHelper.instance.create(ApiService::class.java)
                .getSearchList(page, content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HomeListBean> {
                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onComplete() {
                        mView.loadComplete()
                    }

                    override fun onNext(value: HomeListBean?) {
                        mView.searchResult(value!!)
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
                    }

                })

    }

    override fun collect(id: Int, position: Int) {
        mView.loading()
        RetrofitHelper.instance.create(ApiService::class.java)
                .addCollectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HomeListBean> {
                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onComplete() {
                        mView.loadComplete()
                    }

                    override fun onNext(value: HomeListBean?) {
                        mView.collectSuccess(value!!,position)
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
                    }

                })
    }

    override fun cancelCollect(id: Int, position: Int) {
        mView.loading()
        RetrofitHelper.instance.create(ApiService::class.java)
                .removeCollectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HomeListBean> {
                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onComplete() {
                        mView.loadComplete()
                    }

                    override fun onNext(value: HomeListBean?) {
                        mView.cancelCollectSuccess(value!!,position)
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
                    }

                })


    }


}