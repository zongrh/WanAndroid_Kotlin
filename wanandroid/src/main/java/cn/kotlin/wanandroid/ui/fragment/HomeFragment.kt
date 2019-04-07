package cn.kotlin.wanandroid.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import cn.kotlin.wanandroid.R
import cn.kotlin.wanandroid.adapter.HomeAdapter
import cn.kotlin.wanandroid.bean.AccountBean
import cn.kotlin.wanandroid.bean.BannerBean
import cn.kotlin.wanandroid.bean.HomeListBean
import cn.kotlin.wanandroid.presenter.HomePresenterImpl
import cn.kotlin.wanandroid.ui.WebContentActivity
import cn.kotlin.wanandroid.utils.Constant
import cn.kotlin.wanandroid.utils.Utils
import cn.kotlin.wanandroid.view.HomeView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragement_home.*

/**
 *
 * FileName: HomeFragment
 * Author: nanzong
 * Date: 2019/4/2 9:09 AM
 * Description:
 * History:
 *
 */
class HomeFragment : Fragment(), HomeView {

    private val AUTO_PLAY = 2

    private var currentPage = 0
    private val bannerHandler: Handler? = Handler {
        if (it.what == AUTO_PLAY) {
            headerViewPager.currentItem = headerViewPager.currentItem + 1
            it.target.sendEmptyMessageDelayed(AUTO_PLAY, 5000)

        }
        false
    }


    private val homeFragmentPresenter: HomePresenterImpl by lazy {
        HomePresenterImpl(this)
    }

    /**
     * header viewpager
     */
    private lateinit var headerViewPager: ViewPager

    /**
     * banner Text
     */
    private lateinit var bannerText: TextView

    /**
     *  indictor container
     */
    private lateinit var indictorContainer: LinearLayout
    /**
     * adapter
     */
    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(context, null)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragement_home, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        homeFragmentPresenter.getBanner()
        homeFragmentPresenter.getHomeList(currentPage)

        home_rv.layoutManager = LinearLayoutManager(activity)
        home_rv.adapter = homeAdapter

        home_refresh.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        var headerView = View.inflate(activity, R.layout.fragment_home_header, null)
        headerViewPager = headerView.findViewById(R.id.home_header)
        bannerText = headerView.findViewById(R.id.banner_text)
        indictorContainer = headerView.findViewById(R.id.banner_indictor)
        homeAdapter.addHeaderView(headerView)
        //开启自动轮播
        bannerHandler!!.sendEmptyMessageDelayed(AUTO_PLAY, 5000)
        //下来刷新
        home_refresh.run {
            setOnRefreshListener {
                refresh()
            }
        }
        //上拉加载
        homeAdapter.run {
            setOnLoadMoreListener {
                currentPage++
                homeFragmentPresenter.getHomeList(currentPage)
            }
        }
        //条目点击事件
        homeAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                val datas = homeAdapter.data[position]
//                var intent= Intent(context,WebContentActivity::class.java)
//                todo 点击事件
            }
//            todo 收藏、取消收藏
            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.home_item_collect -> {
                        if (AccountBean.instance.isLogin) {
                            val datas = homeAdapter.data[position]
                        }
                    }
                    R.id.home_item_type -> {
//                        todo 条目类型
                    }

                }
            }

        }

    }

    //viewpager adapter
    class BannerAdapter(var context: FragmentActivity?, var data: List<BannerBean.Data>?) : PagerAdapter() {

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }
        override fun getCount(): Int {
            return Int.MAX_VALUE
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var imageView = ImageView(context)
            Glide.with(context).load(data!![position % data!!.size].imagePath).centerCrop().into(imageView)
            container!!.addView(imageView)
            imageView.setOnClickListener {
                Utils.toast(data!![position].title)
                var intent = Intent(context, WebContentActivity::class.java)
                intent.putExtra(Constant.data, data!![position % data!!.size].title)
                intent.putExtra(Constant.url, data!![position % data!!.size].url)
                intent.putExtra(Constant.id, data!![position % data!!.size].id)
                context!!.startActivity(intent)
            }
            return imageView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container!!.removeView(`object` as ImageView)
        }

    }

    override fun loadHomeListSuccess(result: HomeListBean) {
        if (currentPage == 0) {
            homeAdapter.setNewData(result.data.datas)
        } else {
            homeAdapter.addData(result.data.datas!!)
            if (result.data.over) {
                homeAdapter.loadMoreEnd()
            } else {
                homeAdapter.loadMoreComplete()
            }
        }
    }

    override fun collectSuccess(result: HomeListBean, position: Int) {
    }

    override fun cancelCollectSuccess(result: HomeListBean, position: Int) {
    }

    fun refresh() {
        currentPage = 0
        homeFragmentPresenter.getBanner()
        homeFragmentPresenter.getHomeList(currentPage)
    }

    override fun loadComplete() {
        home_refresh.isRefreshing = false
    }

    override fun loadError(msg: String) {
        Utils.toast(msg)
        home_refresh.isRefreshing = false
    }

    override fun loading() {
        home_refresh.isRefreshing = true
    }

    override fun loadBannerSuccess(result: BannerBean) {
        headerViewPager.adapter = BannerAdapter(activity, result.data)
        bannerText.run {
            text = result.data!![0].title
        }
        //添加指示器
        if (indictorContainer.childCount == 0) {
            for (i in 0 until result.data!!.size) {
                var point = ImageView(activity)
                point.setImageResource(R.drawable.banner_indictor)
                if (i == 0) {
                    point.isSelected = true
                }
                var params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)
                params.leftMargin = 10
                point.layoutParams = params
                indictorContainer.addView(point)
            }
        }
        //view pager 监听
        headerViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(position: Int) {
                bannerText.text = result.data!![position % result.data!!.size].title
                for (i in 0 until result.data!!.size) {
                    indictorContainer.getChildAt(i).isSelected = false
                }
                indictorContainer.getChildAt(position % result.data!!.size).isSelected = true
            }
        })
    }

    fun removeMessage() {
        bannerHandler!!.removeCallbacksAndMessages(null)
    }

    override fun onPause() {
        super.onPause()
        removeMessage()
    }

    override fun onResume() {
        super.onResume()
        removeMessage()
        bannerHandler!!.sendEmptyMessageDelayed(AUTO_PLAY, 5000)

    }

}

