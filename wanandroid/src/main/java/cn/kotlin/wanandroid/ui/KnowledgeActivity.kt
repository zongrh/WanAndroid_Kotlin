package cn.kotlin.wanandroid.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import cn.kotlin.wanandroid.R
import cn.kotlin.wanandroid.bean.HomeListBean
import cn.kotlin.wanandroid.bean.KnowledgeBean
import cn.kotlin.wanandroid.ui.fragment.ContentFragment
import cn.kotlin.wanandroid.utils.Constant
import cn.kotlin.wanandroid.utils.Utils
import com.google.gson.annotations.Until
import kotlinx.android.synthetic.main.activity_knowledge.*

class KnowledgeActivity : AppCompatActivity() {

    private lateinit var datas: KnowledgeBean.Data
    private lateinit var data: HomeListBean.Datas
    private var fragments: ArrayList<Fragment> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knowledge)
        setSupportActionBar(knowledge_bar)
        knowledge_bar.setNavigationOnClickListener {
            finish()
        }

        intent.extras.let {
            if (it.get(Constant.data) is KnowledgeBean.Data) {
                datas = it.get(Constant.data) as KnowledgeBean.Data
                Utils.logE(datas.toString())
                knowledge_bar.title = datas.name
            } else {
                data = it.get(Constant.data) as HomeListBean.Datas
                val kDatas = KnowledgeBean.Data.Children(data.chapterId, data.chapterName!!, 0, 0, 0, 0)
                val list = arrayListOf<KnowledgeBean.Data.Children>()
                list.add(kDatas)
                datas = KnowledgeBean.Data(0, "", 0, 0, 0, 0, list)
                knowledge_bar.title = data.chapterName
            }
        }
        //添加子FragmentView
        for (i in 0 until datas.children!!.size) {
            fragments.add(ContentFragment.newInstance(datas.children!![i].id))
        }


        val vpAdapter = VpAdapter(datas, fragments, supportFragmentManager)
        knowledge_vp.adapter = vpAdapter
        knowledge_tablayout.setupWithViewPager(knowledge_vp)
        knowledge_vp.offscreenPageLimit=datas.children!!.size

    }

    class VpAdapter(datas: KnowledgeBean.Data, fragments: ArrayList<Fragment>, manger: FragmentManager) :
            FragmentPagerAdapter(manger) {
        private var datas: KnowledgeBean.Data = datas

        private var fragments = fragments

        override fun getItem(p0: Int): Fragment {
            return fragments[p0]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return datas.children!![position].name
        }
    }


}
