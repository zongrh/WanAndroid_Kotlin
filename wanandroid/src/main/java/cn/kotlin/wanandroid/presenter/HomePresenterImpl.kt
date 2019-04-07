package cn.kotlin.wanandroid.presenter

import cn.kotlin.wanandroid.bean.BannerBean
import cn.kotlin.wanandroid.bean.HomeListBean
import cn.kotlin.wanandroid.net.ApiService
import cn.kotlin.wanandroid.net.RetrofitHelper
import cn.kotlin.wanandroid.utils.Utils
import cn.kotlin.wanandroid.view.HomeView
import cn.kotlin.wanandroid.view.iBaseView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 *
 * FileName: HomePresenterImpl
 * Author: nanzong
 * Date: 2019/4/3 11:08 AM
 * Description:
 * History:
 *
 */
class HomePresenterImpl(view: iBaseView) : HomePresenter {

    //收藏
    override fun collect(id: Int, position: Int) {
        mView.loading()
        RetrofitHelper.instance.create(ApiService::class.java)
                .addCollectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HomeListBean> {
                    override fun onSubscribe(d: Disposable?) {}

                    override fun onNext(value: HomeListBean?) {
                        mView.collectSuccess(value!!, position)
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
                    }

                    override fun onComplete() {
                        mView.loadComplete()
                    }
                })
    }

    //取消收藏
    override fun cancelCollect(id: Int, position: Int) {
        mView.loading()
        RetrofitHelper.instance.create(ApiService::class.java)
                .removeCollectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HomeListBean> {
                    override fun onSubscribe(d: Disposable?) {}

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

    private var mView: HomeView = view as HomeView

    //获取banner
    override fun getBanner() {
        mView.loading()
        RetrofitHelper.instance.create(ApiService::class.java)
                .getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<BannerBean> {
                    override fun onComplete() {
                        mView.loadComplete()
                    }

                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(value: BannerBean?) {
                        mView.loadBannerSuccess(value!!)
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
                    }

                })

    }


    //获取数据列表
    override fun getHomeList(curPage: Int) {
        mView.loading()
        RetrofitHelper.instance.create(ApiService::class.java)
                .getHomeList(curPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HomeListBean> {
                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onComplete() {
                        mView.loadComplete()
                    }

                    override fun onNext(value: HomeListBean?) {
                        mView.loadHomeListSuccess(value!!)
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
                    }

                })

    }


}
