package cn.kotlin.wanandroid.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.kotlin.wanandroid.R
import cn.kotlin.wanandroid.adapter.HomeAdapter
import cn.kotlin.wanandroid.bean.AccountBean
import cn.kotlin.wanandroid.bean.HomeListBean
import cn.kotlin.wanandroid.bean.KnowledgeBean
import cn.kotlin.wanandroid.presenter.KnowledgePresenterImpl
import cn.kotlin.wanandroid.ui.AccountActivity
import cn.kotlin.wanandroid.ui.WebContentActivity
import cn.kotlin.wanandroid.utils.Constant
import cn.kotlin.wanandroid.utils.Utils
import cn.kotlin.wanandroid.view.KnowLedgeView
import kotlinx.android.synthetic.main.fragement_content.*

/**
 *
 * FileName: ContentFragment
 * Author: nanzong
 * Date: 2019/4/2 9:15 AM
 * Description:
 * History:
 *
 */
class ContentFragment : Fragment(), KnowLedgeView {

    private var mRootView: View? = null
    private var cid = 0
    private var isVisibletoUser: Boolean = false
    private var isViewCreated: Boolean = false
    private var currentPage = 0


    //presenter
    private val presenter: KnowledgePresenterImpl by lazy {
        KnowledgePresenterImpl(this)
    }
    //adapter
    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(activity, null)
    }

    companion object {
        fun newInstance(cid: Int): ContentFragment {
            var bundle = Bundle()
            bundle.putInt(Constant.id, cid)
            val fragment = ContentFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cid = it.getInt(Constant.id)

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragement_content, container, false)
        }
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
        lazyLoad()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isVisibletoUser = isVisibleToUser
        lazyLoad()
    }

    fun lazyLoad() {
        if (isVisibletoUser && isViewCreated) {
            presenter.getKnowledgeContent(currentPage, cid)
            isViewCreated = false
            isVisibletoUser = false

            content_rv.layoutManager = LinearLayoutManager(activity)
            content_rv.adapter = homeAdapter
            content_refresh.setColorSchemeColors(resources.getColor(R.color.colorPrimary))

            //下拉刷新
            content_refresh.run {
                setOnRefreshListener {
                    currentPage = 0
                    presenter.getKnowledgeContent(currentPage, cid)
                }
            }
            //上拉加载
            homeAdapter.run {
                setOnLoadMoreListener {
                    currentPage++
                    presenter.getKnowledgeContent(currentPage, cid)
                }
            }

            //item 点击事件
            homeAdapter.setOnItemClickListener { adapter, view, position ->
                val datas = homeAdapter.data[position]
                var intent = Intent(context, WebContentActivity::class.java)
                intent.putExtra(Constant.data, datas.title)
                intent.putExtra(Constant.url, datas.link)
                intent.putExtra(Constant.name, datas.author)
                intent.putExtra(Constant.id, datas.id)
                startActivity(intent)
            }
            //item 中控件点击事件
            homeAdapter.setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.home_item_collect -> {
                        if (AccountBean.instance.isLogin) {
                            val datas = homeAdapter.data[position]
                            if (datas.collect) {
                                presenter.cancelCollect(datas.id, position)
                            } else {
                                presenter.collect(datas.id, position)
                            }
                        } else {
                            Utils.toast("请先登录")
                            startActivity(Intent(activity, AccountActivity::class.java))
                        }
                    }
                }
            }
        }
    }

    override fun getKnowledgeSuc(result: KnowledgeBean) {
    }

    override fun getKnowledgeContent(result: HomeListBean) {
        if (currentPage == 0) {
            homeAdapter.setNewData(result.data.datas)
            if (result.data.over) {
                homeAdapter.setEnableLoadMore(false)
            }
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
        homeAdapter.data[position].collect = true
        homeAdapter.notifyDataSetChanged()
    }

    override fun cancelCollcetSuccess(result: HomeListBean, position: Int) {
        homeAdapter.data[position].collect = false
        homeAdapter.notifyDataSetChanged()
    }

    override fun loading() {
        if (currentPage == 0)
            content_refresh.isRefreshing = true
    }

    override fun loadComplete() {
        content_refresh.isRefreshing = false
    }

    override fun loadError(msg: String) {
        content_refresh.isRefreshing = false
    }
}