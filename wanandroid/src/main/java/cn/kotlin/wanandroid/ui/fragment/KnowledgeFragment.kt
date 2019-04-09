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
import cn.kotlin.wanandroid.adapter.KnowledgeAdapter
import cn.kotlin.wanandroid.bean.HomeListBean
import cn.kotlin.wanandroid.bean.KnowledgeBean
import cn.kotlin.wanandroid.presenter.KnowledgePresenterImpl
import cn.kotlin.wanandroid.ui.KnowledgeActivity
import cn.kotlin.wanandroid.utils.Constant
import cn.kotlin.wanandroid.utils.Utils
import cn.kotlin.wanandroid.view.KnowLedgeView
import kotlinx.android.synthetic.main.fragement_knowledge.*

/**
 *
 * FileName: ContentFragment
 * Author: nanzong
 * Date: 2019/4/2 9:15 AM
 * Description:
 * History:
 *
 */
class KnowledgeFragment : Fragment(), KnowLedgeView {

    private val presenter: KnowledgePresenterImpl by lazy {
        KnowledgePresenterImpl(this)
    }

    private val knowledgeAdapter: KnowledgeAdapter by lazy {
        KnowledgeAdapter(activity, null)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragement_knowledge, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.getKnowledge()
        knowledge_rv.layoutManager = LinearLayoutManager(activity)
        knowledge_rv.adapter = knowledgeAdapter

//        knowledge_refresh.setColorSchemeColors(ContextCompat.getColor(activity, R.color.colorPrimary))
        knowledge_refresh.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        //下拉刷新
        knowledge_refresh.run {
            setOnRefreshListener {
                presenter.getKnowledge()
            }
        }
        //点击事件
        knowledgeAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                var intent = Intent(activity, KnowledgeActivity::class.java)
                intent.putExtra(Constant.data, knowledgeAdapter.data[position])
                startActivity(intent)
            }
        }
    }

    override fun getKnowledgeSuc(result: KnowledgeBean) {
        knowledgeAdapter.setNewData(result.data)
    }

    override fun getKnowledgeContent(result: HomeListBean) {
    }

    override fun collectSuccess(result: HomeListBean, position: Int) {
    }

    override fun cancelCollcetSuccess(result: HomeListBean, position: Int) {
    }

    override fun loading() {

    }

    override fun loadComplete() {
        knowledge_refresh.isRefreshing = false
    }

    override fun loadError(msg: String) {
        Utils.toast(msg)
        knowledge_refresh.isRefreshing = false
    }
}