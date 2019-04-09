package cn.kotlin.wanandroid.view

import cn.kotlin.wanandroid.bean.HomeListBean
import cn.kotlin.wanandroid.bean.KnowledgeBean

/**
 *
 * FileName: KnowLedgeView
 * Author: nanzong
 * Date: 2019/4/8 4:11 PM
 * Description:
 * History:
 *
 */
interface KnowLedgeView : iBaseView {

    fun getKnowledgeSuc(result: KnowledgeBean)

    fun getKnowledgeContent(result: HomeListBean)

    fun collectSuccess(result: HomeListBean, position: Int)

    fun cancelCollcetSuccess(result: HomeListBean, position: Int)

}