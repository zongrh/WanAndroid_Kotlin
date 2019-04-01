package cn.kotlin.wanadroid.presenter

import cn.kotlin.wanadroid.bean.LoginResponse
import cn.kotlin.wanadroid.net.ApiService
import cn.kotlin.wanadroid.net.RetrofitHelper
import cn.kotlin.wanadroid.view.AccountView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 *
 * FileName: AccountPresenterImpl
 * Author: nanzong
 * Date: 2019/3/31 10:21 PM
 * Description: ${DESCRIPTION}
 * History:
 *
 */
class AccountPresenterImpl(view: AccountView) : AccountPresenter {

    private var mView: AccountView = view

    override fun login(username: String, password: String) {

        RetrofitHelper.instance.create(ApiService::class.java)
                .loginWanAndroid(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<LoginResponse> {
                    override fun onSubscribe(d: Disposable?) {}


                    override fun onNext(value: LoginResponse?){
                        mView.LoginSuccess(value!!)
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
                    }
                    override fun onComplete() {}
                })


    }

    override fun register(username: String, password: String, repassword: String) {
        RetrofitHelper.instance.create(ApiService::class.java)
                .registerWanAndroid(username, password, repassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<LoginResponse> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(value: LoginResponse?) {
                        mView.registerSuccess(value!!)
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
                    }

                })

    }
}