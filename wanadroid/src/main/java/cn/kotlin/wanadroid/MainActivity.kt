package cn.kotlin.wanadroid

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import cn.kotlin.wanadroid.bean.AccountBean
import cn.kotlin.wanadroid.ui.AccountActivity
import cn.kotlin.wanadroid.ui.fragment.HomeFragment
import cn.kotlin.wanadroid.ui.fragment.HotFragment
import cn.kotlin.wanadroid.ui.fragment.KnowledgeFragment
import cn.kotlin.wanadroid.utils.Utils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //    延迟初始化  Kotlin中有两种延迟初始化的方式。一种是lateinit var，一种是by lazy。
    private lateinit var mNavIv: ImageView
    private lateinit var mNavTv: TextView
    private lateinit var mTvLoginout: TextView
    private var homeFragment: HomeFragment = HomeFragment()
    private var knowledgeFragment: KnowledgeFragment = KnowledgeFragment()
    private var mineFragment: HotFragment = HotFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //设置监听
        toolbar.run {
            setSupportActionBar(this)
        }
        drawer.run {
            //监听事件
            val toggle = ActionBarDrawerToggle(
                    this@MainActivity,
                    this,
                    toolbar,
                    R.string.my_like,
                    R.string.my_about
            )
            //绑定监听
            addDrawerListener(toggle)
            toggle.syncState()
        }
        //设置onDrawerNavigationItemSelectedListener监听
        navigationview.run {
            setNavigationItemSelectedListener(onDrawerNavigationItemSelectedListener)
        }
        //绑定控件
        mNavIv = navigationview.getHeaderView(0).findViewById(R.id.header_iv)
        mNavTv = navigationview.getHeaderView(0).findViewById(R.id.header_tv)
        mTvLoginout = navigationview.getHeaderView(0).findViewById(R.id.tv_login_out)
        //跳转登录
        mNavTv.setOnClickListener {
            if (!AccountBean.instance.isLogin) {
                startActivityForResult(Intent(this, AccountActivity::class.java), 11)
            }
        }
        mTvLoginout.setOnClickListener {
            AccountBean.instance.clear()
            mNavTv.text = getString(R.string.login)
            mTvLoginout.visibility = View.GONE
//            todo 2019-04-02 16:42:44
//            homeFragment.refresh()
            //清除 cookie 、登录缓存
        }
        if (AccountBean.instance.isLogin) {
            mNavTv.text = AccountBean.instance.username
            mTvLoginout.visibility = View.VISIBLE
        } else {
            mNavTv.text = getString(R.string.login)
        }
        //初始化底部Tab
        bottom_navigation.run {
            setOnNavigationItemSelectedListener(bottomNavigationSelectedListener)
        }
        if (supportFragmentManager.fragments.size == 0) {
            supportFragmentManager.beginTransaction().add(R.id.fl_container, homeFragment).commitAllowingStateLoss()
            supportFragmentManager.beginTransaction().add(R.id.fl_container, knowledgeFragment).commitAllowingStateLoss()
            supportFragmentManager.beginTransaction().add(R.id.fl_container, mineFragment).commitAllowingStateLoss()
            supportFragmentManager.beginTransaction().hide(knowledgeFragment).commitAllowingStateLoss()
            supportFragmentManager.beginTransaction().hide(mineFragment).commitAllowingStateLoss()
        }
    }

    //底部 tab 点击事件
    private val bottomNavigationSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                supportFragmentManager.beginTransaction().show(homeFragment).commitAllowingStateLoss()
                supportFragmentManager.beginTransaction().hide(knowledgeFragment).commitAllowingStateLoss()
                supportFragmentManager.beginTransaction().hide(mineFragment).commitAllowingStateLoss()
                toolbar.title = getString(R.string.app_name)
            }
            R.id.navigation_type -> {
                supportFragmentManager.beginTransaction().hide(homeFragment).commitAllowingStateLoss()
                supportFragmentManager.beginTransaction().show(knowledgeFragment).commitAllowingStateLoss()
                supportFragmentManager.beginTransaction().hide(mineFragment).commitAllowingStateLoss()
                toolbar.title = getString(R.string.Konwledge)
            }
            R.id.navigation_hot -> {
                supportFragmentManager.beginTransaction().hide(homeFragment).commitAllowingStateLoss()
                supportFragmentManager.beginTransaction().hide(knowledgeFragment).commitAllowingStateLoss()
                supportFragmentManager.beginTransaction().show(mineFragment).commitAllowingStateLoss()
                toolbar.title = getString(R.string.hot)
            }
        }
        true
    }


    private val onDrawerNavigationItemSelectedListener =
            NavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_like -> {
                        if (AccountBean.instance.isLogin) {
                            //我的收藏
//                            todo
//                            startActivity(Intent(this, CollectActivity::class.java))
                            Utils.toast("已经登录")

                        } else {
                            Utils.toast("请先登录")
                            startActivity(Intent(this, AccountActivity::class.java))
                        }
                    }

                    R.id.nav_about -> {
                        Utils.toast("about")
                    }
                }
                true
            }

}
