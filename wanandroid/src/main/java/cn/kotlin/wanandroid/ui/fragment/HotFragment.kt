package cn.kotlin.wanandroid.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.kotlin.wanandroid.R
import cn.kotlin.wanandroid.bean.HotBean
import cn.kotlin.wanandroid.presenter.HotPresenter
import cn.kotlin.wanandroid.presenter.HotPresenterImpl
import cn.kotlin.wanandroid.ui.WebContentActivity
import cn.kotlin.wanandroid.ui.view.FlowLayout
import cn.kotlin.wanandroid.utils.Constant
import cn.kotlin.wanandroid.utils.Utils
import cn.kotlin.wanandroid.view.HotView
import kotlinx.android.synthetic.main.fragement_hot.*

/**
 *
 * FileName: ContentFragment
 * Author: nanzong
 * Date: 2019/4/2 9:15 AM
 * Description:
 * History:
 *
 */
class HotFragment : Fragment(), HotView {

    private var isFirst = true

    private val presenter: HotPresenterImpl by lazy {
        HotPresenterImpl(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragement_hot, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hot_refresh.setOnRefreshListener {
            presenter.getHotKey()
            presenter.getCommonUse()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!!hidden && isFirst) {
            presenter.getHotKey()
            presenter.getCommonUse()
            isFirst = false
        }
    }

    override fun getHotKeySuc(result: HotBean) {
        Utils.logE(result)
        setContainer(search_container, result)
    }

    override fun getCommonUse(result: HotBean) {
        Utils.logE(result)
        setContainer(common_container, result)
    }

    fun setContainer(container: FlowLayout, result: HotBean) {
        container.removeAllViews()

        result.data!!.forEach {
            var tag = TextView(activity)
            var params = ViewGroup.MarginLayoutParams(-2, -2)
            tag.textSize = 14f
            tag.setTextColor(resources.getColor(R.color.green))
            tag.text = it.name
            params.leftMargin = Utils.dp2px(6)
            params.topMargin = Utils.dp2px(6)
            tag.setPadding(Utils.dp2px(10), Utils.dp2px(10), Utils.dp2px(10), Utils.dp2px(10))
            tag.setBackgroundResource(R.drawable.shape_hot_tag)
            container.addView(tag, params)

            tag.setOnClickListener {
                var position = container.indexOfChild(tag)
                var intent = Intent(context, WebContentActivity::class.java)
                intent.putExtra(Constant.data, result.data[position]!!.name)
                intent.putExtra(Constant.url, result.data[position]!!.link.toString())
                intent.putExtra(Constant.id, result.data[position]!!.id)
                startActivity(intent)
            }
        }
    }

    override fun loading() {
        hot_refresh.isRefreshing = true
    }

    override fun loadComplete() {
        hot_refresh.isRefreshing = false
    }

    override fun loadError(msg: String) {
        hot_refresh.isRefreshing = false
    }
}