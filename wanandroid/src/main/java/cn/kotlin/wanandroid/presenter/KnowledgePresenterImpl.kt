package cn.kotlin.wanandroid.presenter

import cn.kotlin.wanandroid.bean.HomeListBean
import cn.kotlin.wanandroid.bean.KnowledgeBean
import cn.kotlin.wanandroid.net.ApiService
import cn.kotlin.wanandroid.net.RetrofitHelper
import cn.kotlin.wanandroid.view.KnowLedgeView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 *
 * FileName: KnowledgePresenterImpl
 * Author: nanzong
 * Date: 2019/4/8 8:56 PM
 * Description:
 * History:
 *
 */
class KnowledgePresenterImpl(view: KnowLedgeView) : KnowledgePresenter {
    private var mView: KnowLedgeView = view

    //获取 知识体系
    override fun getKnowledge() {
        mView.loading()
        RetrofitHelper.instance.create(ApiService::class.java)
                .getTypeTreeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<KnowledgeBean> {
                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onComplete() {
                        mView.loadComplete()
                    }

                    override fun onNext(value: KnowledgeBean?) {
                        mView.getKnowledgeSuc(value!!)
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
                    }

                })


    }

    // 获取知识体系下的文章
    override fun getKnowledgeContent(page: Int, id: Int) {
        mView.loading()
        RetrofitHelper.instance.create(ApiService::class.java)
                .getArticleList(page, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HomeListBean> {
                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onComplete() {
                        mView.loadComplete()
                    }

                    override fun onNext(value: HomeListBean?) {
                        mView.getKnowledgeContent(value!!)
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

                    override fun onNext(value: HomeListBean?) {
                        mView.collectSuccess(value!!, position)
                    }

                    override fun onComplete() {
                        mView.loadComplete()
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
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
                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onComplete() {
                        mView.loadComplete()
                    }

                    override fun onNext(value: HomeListBean?) {
                        mView.cancelCollcetSuccess(value!!, position)
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
                    }

                })


    }


}