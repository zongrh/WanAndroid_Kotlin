package cn.kotlin.wanandroid.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import cn.kotlin.wanandroid.R
import cn.kotlin.wanandroid.bean.AccountBean
import cn.kotlin.wanandroid.bean.LoginResponse
import cn.kotlin.wanandroid.presenter.AccountPresenterImpl
import cn.kotlin.wanandroid.utils.Constant
import cn.kotlin.wanandroid.utils.Utils
import cn.kotlin.wanandroid.view.AccountView
import kotlinx.android.synthetic.main.activity_login.*

class AccountActivity : AppCompatActivity(), AccountView {
    /**
     * by lazy  只获取不赋值   lazy 只用于常量 val
     * lazy 应用于单例模式(if-null-then-init-else-return)，
     * 而且当且仅当变量被第一次调用的时候，委托方法才会执行。
     *
     * presenterf
     */
    private val accountPresenter: AccountPresenterImpl by lazy {
        AccountPresenterImpl(this)
    }

    /**
     * 登录成功后的操作
     */
    override fun LoginSuccess(result: LoginResponse) {
        if (result.errorCode == 0) {
            Utils.logE(result)
            Utils.toast("登录成功")
            Utils.logE(result.toString())
            initAccount(result)
            setResult(Constant.AccountCode)
            finish()
        } else {
            Utils.toast(result.errorMsg!!)
        }
    }

    //  保存登录成功后个人信息数据
    private fun initAccount(result: LoginResponse) {
        AccountBean.instance.username = result.data.username
        AccountBean.instance.id = result.data.id
        AccountBean.instance.type = result.data.type
        AccountBean.instance.icon = result.data.icon!!//新引入运算符“!!”，通知编译器不做非空校验，运行时一旦发现实例为空就扔出异常；
        AccountBean.instance.isLogin = true
        AccountBean.instance.collectIds = result.data.collectIds
        Utils.writeToCache(Constant.accountInfo, AccountBean.instance)

    }

    override fun registerSuccess(result: LoginResponse) {
        if (result.errorCode == 0) {
            Utils.logE(result.data)
            Utils.toast("注册成功")
            initAccount(result)
            setResult(Constant.AccountCode)
            finish()
        } else {
            Utils.toast(result.errorMsg!!)
        }
    }

    override fun loading() {
    }

    override fun loadComplete() {
    }

    override fun loadError(msg: String) {
        Utils.toast(msg)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setSupportActionBar(login_bar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        login_bar.setNavigationOnClickListener {
            finish()
        }

        bt_register.setOnClickListener {
            if (checkInput()) {
                accountPresenter.register(et_username.text.toString(),
                        et_pwd.text.toString(), et_pwd.text.toString())
            }
        }
        bt_login.setOnClickListener {
            if (checkInput()) {
                accountPresenter.login(et_username.text.toString(), et_pwd.text.toString())
            }
        }

    }

    fun checkInput(): Boolean {
        if (TextUtils.isEmpty(et_username.text.toString())) {
            Utils.toast("用户名不能为空")
            return false
        }
        if (TextUtils.isEmpty(et_pwd.text.toString())) {
            Utils.toast("密码不能为空")
            return false
        }
        return true
    }

}
