package cn.kotlin.wanandroid.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import cn.kotlin.wanandroid.R
import cn.kotlin.wanandroid.bean.HomeListBean
import cn.kotlin.wanandroid.presenter.CollectPresenterImpl
import cn.kotlin.wanandroid.utils.Utils
import cn.kotlin.wanandroid.view.CollectView

class WebContentActivity : AppCompatActivity() ,CollectView{
    override fun getCollectSuc(result: HomeListBean) {
    }

    override fun collectSuccess(result: HomeListBean, position: Int) {
    }

    override fun cancelCollectSuccess(result: HomeListBean, position: Int) {
    }

    override fun loadError(msg: String) {
    }

    override fun loading() {
    }

    override fun loadComplete() {
        Utils.logE("complete")
    }

    override fun collectOutArticle(result: HomeListBean) {
        Utils.logE(result.toString())
    }

    private var shareId = 0
    private lateinit var url: String
    private lateinit var title: String

    private val presenter:CollectPresenterImpl by lazy {
        CollectPresenterImpl(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_content)
    }
}
