package cn.kotlin.wanandroid.adapter

import android.support.v4.app.FragmentActivity
import android.widget.TextView
import cn.kotlin.wanandroid.R
import cn.kotlin.wanandroid.bean.KnowledgeBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import java.lang.StringBuilder

/**
 *
 * FileName: KnowledgeAdapter
 * Author: nanzong
 * Date: 2019/4/9 5:00 PM
 * Description:
 * History:
 *
 */
class KnowledgeAdapter(val context: FragmentActivity?, datas: List<KnowledgeBean.Data>?) :
        BaseQuickAdapter<KnowledgeBean.Data, BaseViewHolder>(R.layout.item_knowledge) {
    override fun convert(helper: BaseViewHolder?, item: KnowledgeBean.Data?) {
        helper?.getView<TextView>(R.id.knowledge_item_title)?.text = item?.name
        var sb = StringBuilder()
        item?.children?.forEach {
            sb.append(it.name + "   ")
        }
        helper?.getView<TextView>(R.id.knowledge_item_content)?.text = sb.toString()

    }


}