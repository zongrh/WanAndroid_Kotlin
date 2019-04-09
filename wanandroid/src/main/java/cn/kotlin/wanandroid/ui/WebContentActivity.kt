package cn.kotlin.wanandroid.ui

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import cn.kotlin.wanandroid.R
import cn.kotlin.wanandroid.bean.AccountBean
import cn.kotlin.wanandroid.bean.HomeListBean
import cn.kotlin.wanandroid.presenter.CollectPresenterImpl
import cn.kotlin.wanandroid.utils.Constant
import cn.kotlin.wanandroid.utils.Utils
import cn.kotlin.wanandroid.view.CollectView
import kotlinx.android.synthetic.main.activity_web_content.*

class WebContentActivity : AppCompatActivity(), CollectView {

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

    private val presenter: CollectPresenterImpl by lazy {
        CollectPresenterImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_content)

        intent.extras.let {
            shareId = it.getInt(Constant.id)
            url = it.getString(Constant.url)
            title = it.getString(Constant.data)
        }

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.title = title
        toolbar.setNavigationOnClickListener {
            if (webview.canGoBack()) {
                webview.goBack()
            } else {
                finish()
            }
        }
        //load url
        webview.loadUrl(url)
        var webSettings = webview.settings
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.javaScriptEnabled = true
        //设置自适应屏幕，两者合用
        webSettings.useWideViewPort = true //将图片调整到适合webview的大小
        webSettings.loadWithOverviewMode = true // 缩放至屏幕的大小
        webSettings.javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口

        webview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                super.onReceivedSslError(view, handler, error)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }
        }

        webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                progress.setCurrentX(newProgress)
                if (newProgress == 100) {
                    progress.visibility = View.GONE
                }
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                toolbar.title = title
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_content, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.collect -> {
                if (AccountBean.instance.isLogin) {
                    presenter.collectOutArticle(title, "", url)
                } else {
                    Utils.toast("请先登录")
                    startActivity(Intent(this, AccountActivity::class.java))
                }
            }

            R.id.broswer -> {
                var uri = Uri.parse(url)
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }

            R.id.share -> {
                var shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                startActivity(shareIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
