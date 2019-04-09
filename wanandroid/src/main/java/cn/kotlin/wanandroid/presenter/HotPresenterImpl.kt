package cn.kotlin.wanandroid.presenter

import cn.kotlin.wanandroid.bean.HotBean
import cn.kotlin.wanandroid.net.ApiService
import cn.kotlin.wanandroid.net.RetrofitHelper
import cn.kotlin.wanandroid.view.HotView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 *
 * FileName: HotPresenterImpl
 * Author: nanzong
 * Date: 2019/4/9 9:21 PM
 * Description:
 * History:
 *
 */
class HotPresenterImpl(view: HotView) : HotPresenter {

    private var mView: HotView = view

    override fun getHotKey() {
        mView.loading()
        RetrofitHelper.instance.create(ApiService::class.java)
                .getHotKeyList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HotBean> {
                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onComplete() {
                        mView.loadComplete()
                    }

                    override fun onNext(value: HotBean?) {
                        mView.getHotKeySuc(value!!)
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
                    }
                })
    }

    override fun getCommonUse() {
        mView.loading()
        RetrofitHelper.instance.create(ApiService::class.java)
                .getFriendList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HotBean> {
                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onComplete() {
                        mView.loadComplete()
                    }

                    override fun onNext(value: HotBean?) {
                        mView.getCommonUse(value!!)
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
                    }
                })

    }

}